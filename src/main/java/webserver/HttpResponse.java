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

	public static void responseLogin302Header(DataOutputStream dos, LoginStatus loginStatus, HttpSession httpSession,
		Logger logger) {
		try {
			dos.writeBytes("HTTP/1.1 302 FOUND " + LINE_SEPARATOR);
			dos.writeBytes(String.format("Location: %s" + LINE_SEPARATOR, loginStatus.getRedirectUrl()));
			dos.writeBytes(String.format("Set-Cookie: sessionId=%s; Path=/" + LINE_SEPARATOR, httpSession.getId()));
			dos.writeBytes(LINE_SEPARATOR);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public static void responseUserList200Header(DataOutputStream dos, byte[] body, Logger logger) {
		try {
			dos.writeBytes("HTTP/1.1 200 OK " + LINE_SEPARATOR);
			dos.writeBytes("Content-Type: text/html;charset=UTF-8" + LINE_SEPARATOR);
			dos.write(body, 0, body.length);
			dos.flush();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public static void responseNotLogin302Header(DataOutputStream dos, Logger logger) {
		try {
			dos.writeBytes("HTTP/1.1 302 FOUND " + LINE_SEPARATOR);
			dos.writeBytes(String.format("Location: %s" + LINE_SEPARATOR, "/user/login.html"));
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}
