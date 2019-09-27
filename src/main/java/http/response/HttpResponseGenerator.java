package http.response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

import exception.FailResponseException;
import http.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

public class HttpResponseGenerator {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

	public static Map<Header, String> response200Header(String path, int bodyLength) {
		try {
			Map<Header, String> header = new LinkedHashMap<>();

			String mimeType = Files.probeContentType(Paths.get(path));
			String headerLine = String.format("%s/%s %s %s\r\n", Header.PROTOCOL.getElement(),
					Header.PROTOCOL_VERSION.getElement(), ResponseStatus.OK.getCode(), ResponseStatus.OK.getDescription());
			saveResponseHeader(headerLine, header, mimeType, bodyLength);
			return header;
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		throw new FailResponseException();
	}

	public static Map<Header, String> responseLoginSuccess(String path, int bodyLength) {
		Map<Header, String> header = response200Header(path, bodyLength);
		generateCookie(header);
		return header;
	}

	private static void generateCookie(Map<Header, String> header) {
		String cookie = String.format("logined=true; %s=/\r\n", Header.PATH.getElement());
		header.put(Header.SET_COOKIE, cookie);
	}

	private static void saveResponseHeader(String headerLine, Map<Header, String> header, String mimeType, int lengthOfBodyContent) {
		String[] headerLines = headerLine.split(" ");
		header.put(Header.PROTOCOL, headerLines[0]);
		header.put(Header.CODE, headerLines[1]);
		header.put(Header.DESCRIPTION, headerLines[2]);
		header.put(Header.CONTENT_TYPE, mimeType + ";charset=utf-8\r\n");
		header.put(Header.CONTENT_LENGTH, lengthOfBodyContent + "\r\n");
	}

	public static Map<Header, String> response302Header(String location) {
		Map<Header, String> header = new LinkedHashMap<>();
		header.put(Header.PROTOCOL, "HTTP/1.1");
		header.put(Header.DESCRIPTION, ResponseStatus.FOUND.getDescription());
		header.put(Header.CODE, ResponseStatus.FOUND.getCode());
		header.put(Header.LOCATION, location);
		return header;
	}

}
