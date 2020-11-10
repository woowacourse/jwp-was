package webserver;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import clinet.controller.AbstractController;
import web.controller.Controller;
import web.request.HttpRequest;
import web.response.HttpResponse;

public class RequestHandler implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

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

			Controller controller = AbstractController.findController(request.getPath());
			controller.service(request, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
