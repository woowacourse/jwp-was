package http.response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

import exception.FailResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

public class HttpResponseGenerator {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

	public static Map<String, String> response200Header(String path, int bodyLength) {
		try {
			Map<String, String> header = new LinkedHashMap<>();

			String mimeType = Files.probeContentType(Paths.get(path));

			saveResponseHeader("HTTP/1.1 200 OK \r\n", header, mimeType, bodyLength);
			return header;
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		throw new FailResponseException();
	}

	private static void saveResponseHeader(String headerLine, Map<String, String> header, String mimeType, int lengthOfBodyContent) {
		String[] info = headerLine.split(" ");
		header.put("Http", info[0]);
		header.put("Code", info[1]);
		header.put("Description", info[2]);
		header.put("Content-Type", mimeType + ";charset=utf-8\r\n");
		header.put("Content-Length", lengthOfBodyContent + "\r\n");
	}

	public static Map<String, String> response302Header(String location) {
		Map<String, String> header = new LinkedHashMap<>();
		header.put("Http", "HTTP/1.1");
		header.put("Code", "302");
		header.put("Description", "Found");
		header.put("Location", location);
		return header;
	}
}
