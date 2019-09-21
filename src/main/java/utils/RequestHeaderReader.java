package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

public class RequestHeaderReader {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

	public static List<String> printRequest(BufferedReader bufferedReader) throws IOException {
		List<String> requests = new ArrayList<>();
		String requestLine = bufferedReader.readLine();
		while (requestLine != null && !requestLine.isEmpty()) {
			logger.info("info :{}", requestLine);
			requests.add(requestLine);
			requestLine = bufferedReader.readLine();
		}
		return requests;
	}
}
