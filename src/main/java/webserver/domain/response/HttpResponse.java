package webserver.domain.response;

import java.io.DataOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.StaticFileType;
import webserver.RequestHandler;

public class HttpResponse {

	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
	private static final String NEW_LINE = System.lineSeparator();

	private void response200Header(DataOutputStream dos, StaticFileType staticFileType, int lengthOfBodyContent) {
		try {
			dos.writeBytes("HTTP/1.1 200 OK " + NEW_LINE);
			dos.writeBytes("Content-Type: " + staticFileType.getContentType() + ";charset=utf-8" + NEW_LINE);
			dos.writeBytes("Content-Length: " + lengthOfBodyContent + NEW_LINE);
			dos.writeBytes(NEW_LINE);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private void response302Header(DataOutputStream dos, String redirectUrl) {
		try {
			dos.writeBytes("HTTP/1.1 302 FOUND " + NEW_LINE);
			dos.writeBytes("Location: " + redirectUrl + NEW_LINE);
			dos.writeBytes(NEW_LINE);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private void responseBody(DataOutputStream dos, byte[] body) {
		try {
			dos.write(body, 0, body.length);
			dos.flush();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}
