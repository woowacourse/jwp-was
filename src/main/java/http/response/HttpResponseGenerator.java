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
	//TODO : status code, description ENUM으로 빼기
	public static final String RESPONSE_PROTOCOL = "HTTP";
	public static final String RESPONSE_PROTOCOL_VERSION = "1.1";
	public static final String RESPONSE_CODE = "Code";
	public static final String RESPONSE_DESCRIPTION = "Description";
	public static final String RESPONSE_CONTENT_TYPE = "Content-Type";
	public static final String RESPONSE_CONTENT_LENGTH = "Content-Length";
	public static final String RESPONSE_LOCATION = "Location";

	public static Map<String, String> response200Header(String path, int bodyLength) {
		try {
			Map<String, String> header = new LinkedHashMap<>();

			String mimeType = Files.probeContentType(Paths.get(path));
			String headerLine = String.format("%s/%s 200 OK\r\n", RESPONSE_PROTOCOL, RESPONSE_PROTOCOL_VERSION);
			saveResponseHeader(headerLine, header, mimeType, bodyLength);
			return header;
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		throw new FailResponseException();
	}

	private static void saveResponseHeader(String headerLine, Map<String, String> header, String mimeType, int lengthOfBodyContent) {
		String[] headerLines = headerLine.split(" ");
		header.put(RESPONSE_PROTOCOL, headerLines[0]);
		header.put(RESPONSE_CODE, headerLines[1]);
		header.put(RESPONSE_DESCRIPTION, headerLines[2]);
		header.put(RESPONSE_CONTENT_TYPE, mimeType + ";charset=utf-8\r\n");
		header.put(RESPONSE_CONTENT_LENGTH, lengthOfBodyContent + "\r\n");
	}

	public static Map<String, String> response302Header(String location) {
		Map<String, String> header = new LinkedHashMap<>();
		header.put(RESPONSE_PROTOCOL, "HTTP/1.1");
		header.put(RESPONSE_CODE, "302");
		header.put(RESPONSE_DESCRIPTION, "Found");
		header.put(RESPONSE_LOCATION, location);
		return header;
	}
}
