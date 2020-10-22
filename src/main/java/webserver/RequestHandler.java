package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;
import utils.FileIoUtils;
import utils.RequestPathUtil;
import web.ContentType;
import web.HttpRequest;

public class RequestHandler implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
	private static final String INDEX_HTML_LOCATION = "/index.html";
	private static final String LINE_SEPARATOR = System.lineSeparator();

	private Socket connection;

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
	}

	public void run() {
		logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
			connection.getPort());

		try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
			BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

			HttpRequest request = HttpRequest.of(br);

			DataOutputStream dos = new DataOutputStream(out);

			if ("/user/create".equals(request.getPath())) {
				signUpUser(dos, request);
			} else {
				String filePath = request.getPath();
				String requestPath = RequestPathUtil.extractFilePath(filePath);
				byte[] body = FileIoUtils.loadFileFromClasspath(requestPath);
				String contentType = ContentType.of(filePath)
					.getContentType();

				response200Header(dos, body.length, contentType);
				responseBody(dos, body);
			}
		} catch (IOException | URISyntaxException e) {
			logger.error(e.getMessage());
		}
	}

	private void signUpUser(DataOutputStream dos, HttpRequest request) {
		User user = new User(request.getParameter("userId"), request.getParameter("password"),
			request.getParameter("name"), request.getParameter("email"));
		DataBase.addUser(user);

		logger.debug("Database : {}", user);
		response302Header(dos, INDEX_HTML_LOCATION);
	}

	private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
		try {
			dos.writeBytes("HTTP/1.1 200 OK " + LINE_SEPARATOR);
			dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8" + LINE_SEPARATOR);
			dos.writeBytes("Content-Length: " + lengthOfBodyContent + LINE_SEPARATOR);
			dos.writeBytes(LINE_SEPARATOR);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private void response302Header(DataOutputStream dos, String location) {
		try {
			dos.writeBytes("HTTP/1.1 302 Found " + LINE_SEPARATOR);
			dos.writeBytes("Location: " + location + LINE_SEPARATOR);
			dos.writeBytes("Content-Type: text/html;charset=utf-8" + LINE_SEPARATOR);
			dos.writeBytes(LINE_SEPARATOR);
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
