package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import http.HeaderElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

public class HttpRequestHeaderReader {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

	public static final String REQUEST_SEPARATOR = ": ";

	public static Map<HeaderElement, String> readRequest(BufferedReader bufferedReader) throws IOException {
		Map<HeaderElement, String> requests = new HashMap<>();
		String requestLine = bufferedReader.readLine();

		while (!Objects.isNull(requestLine) && !requestLine.isEmpty()) {
			logger.info("request header - {}", requestLine);
			if (requestLine.isEmpty() || Objects.isNull(requestLine)) {
				break;
			}
			String[] request = requestLine.split(REQUEST_SEPARATOR);
			requests.put(HeaderElement.getHeader(request[0].trim()), request[1].trim());
			requestLine = bufferedReader.readLine();
		}

		return requests;
	}
}
