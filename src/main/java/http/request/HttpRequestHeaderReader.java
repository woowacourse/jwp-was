package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import http.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

public class HttpRequestHeaderReader {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

	public static final String REQUEST_SEPARATOR = ": ";

	public static Map<Header, String> readRequest(BufferedReader bufferedReader) throws IOException {
		Map<Header, String> requests = new HashMap<>();
		String requestLine = bufferedReader.readLine();

		logger.info("request header - {}", requestLine);

		saveRequestURL(requestLine, requests);

		while (!Objects.isNull(requestLine) && !requestLine.isEmpty()) {
			requestLine = bufferedReader.readLine();
			logger.info("request header - {}", requestLine);
			if (requestLine.isEmpty() || Objects.isNull(requestLine)) {
				break;
			}
			String[] request = requestLine.split(REQUEST_SEPARATOR);
			requests.put(Header.getHeader(request[0].trim()), request[1].trim());
		}

		return requests;
	}

	private static void saveRequestURL(String requestLine, Map<Header, String> requests) {
		String[] requestLines = requestLine.split(" ");
		requests.put(Header.METHOD, requestLines[0]);
		requests.put(Header.PATH, requestLines[1]);
		requests.put(Header.PROTOCOL, requestLines[2]);
	}
}
