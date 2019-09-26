package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import controller.Controller;
import controller.ControllerGenerator;
import http.request.HttpRequest;
import http.request.HttpRequestReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
			HttpRequest httpRequest = HttpRequestReader.readHttpRequest(bufferedReader);
			Controller controller = ControllerGenerator.generateController(httpRequest.getRequestHeaderElement("Path"));
			controller.service(httpRequest, out);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}
