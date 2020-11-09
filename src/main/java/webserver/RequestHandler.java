package webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;
import utils.FileIoUtils;
import utils.RequestPathUtil;
import web.request.HttpRequest;
import web.response.HttpResponse;

public class RequestHandler implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
	private static final String INDEX_HTML_LOCATION = "/index.html";

	private Socket connection;

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
	}

	public void run() {
		logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
			connection.getPort());

		try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
			HttpRequest request = HttpRequest.of(in);
			HttpResponse response = HttpResponse.of(out);

			if ("/user/create".equals(request.getPath())) {
				signUpUser(response, request);
			} else {
				String filePath = request.getPath();
				String requestPath = RequestPathUtil.extractFilePath(filePath);
				byte[] body = FileIoUtils.loadFileFromClasspath(requestPath);

				response.response200Header(body.length);
				response.responseBody(body);
			}
		} catch (IOException | URISyntaxException e) {
			logger.error(e.getMessage());
		}
	}

	private void signUpUser(HttpResponse response, HttpRequest request) {
		User user = new User(request.getParameter("userId"), request.getParameter("password"),
			request.getParameter("name"), request.getParameter("email"));
		DataBase.addUser(user);

		logger.debug("Database : {}", user);
		response.sendRedirect(INDEX_HTML_LOCATION);
	}
}
