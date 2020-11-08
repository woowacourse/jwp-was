package webserver;

import java.io.DataOutputStream;
import java.io.IOException;

import org.slf4j.Logger;

public class HttpResponse {
	private static final String LINE_SEPARATOR = System.lineSeparator();

	public static void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType,
		Logger logger) {
		try {
			dos.writeBytes("HTTP/1.1 200 OK " + LINE_SEPARATOR);
			dos.writeBytes(String.format("Content-Type: %s" + LINE_SEPARATOR, contentType));
			dos.writeBytes("Content-Length: " + lengthOfBodyContent + LINE_SEPARATOR);
			dos.writeBytes(LINE_SEPARATOR);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public static void response302Header(DataOutputStream dos, String url, Logger logger) {
		try {
			dos.writeBytes("HTTP/1.1 302 FOUND " + LINE_SEPARATOR);
			dos.writeBytes(String.format("Location: %s" + LINE_SEPARATOR, url));
			dos.writeBytes(LINE_SEPARATOR);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public static void responseLogin302Header(DataOutputStream dos, LoginStatus loginStatus, Logger logger) {
		try {
			dos.writeBytes("HTTP/1.1 302 FOUND " + LINE_SEPARATOR);
			dos.writeBytes(String.format("Location: %s" + LINE_SEPARATOR, loginStatus.getRedirectUrl()));
			dos.writeBytes(String.format("Set-Cookie: logined=%s; Path=/" + LINE_SEPARATOR, loginStatus.isValidated()));
			dos.writeBytes(LINE_SEPARATOR);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}
