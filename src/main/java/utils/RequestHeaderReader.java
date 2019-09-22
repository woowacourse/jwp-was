package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

public class RequestHeaderReader {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
	public static final String REQUEST_METHOD = "Method";
	public static final String REQUEST_PATH = "Path";
	public static final String REQUEST_HTTP = "Http";
	public static final String REQUEST_SEPARATOR = ": ";

	public static Map<String, String> readRequest(BufferedReader bufferedReader) throws IOException {
		Map<String, String> requests = new HashMap<>();
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
			requests.put(request[0].trim(), request[1].trim());
		}

		return requests;
	}

	private static void saveRequestURL(String requestLine, Map<String, String> requests) {
		String[] infos = requestLine.split(" ");
		requests.put(REQUEST_METHOD, infos[0]);
		requests.put(REQUEST_PATH, infos[1]);
		requests.put(REQUEST_HTTP, infos[2]);
	}
}
