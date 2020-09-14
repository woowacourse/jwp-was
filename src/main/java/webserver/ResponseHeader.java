package webserver;

import java.io.DataOutputStream;
import java.io.IOException;

import org.slf4j.Logger;

public class ResponseHeader {
	public static void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType,
		Logger logger) {
		try {
			dos.writeBytes("HTTP/1.1 200 OK \r\n");
			dos.writeBytes(String.format("Content-Type: %s\r\n", contentType));
			dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public static void response302Header(DataOutputStream dos, String url, Logger logger) {
		try {
			dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
			dos.writeBytes(String.format("Location: %s\r\n", url));
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}
