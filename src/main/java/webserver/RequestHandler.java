package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.domain.request.HttpRequest;

public class RequestHandler implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

	private Socket connection;

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
	}

	public void run() {
		logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
			connection.getPort());

		try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream();
			 BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(in, StandardCharsets.UTF_8)))) {

			HttpRequest httpRequest = new HttpRequest(bufferedReader);
			System.out.println(httpRequest.getParameter("name"));

			byte[] responseBody = {};
			DataOutputStream dos = new DataOutputStream(out);

			// if (httpRequestHeader.isStaticFile()) {
			// 	responseBody = FileIoUtils.loadFileFromRequest(httpRequestHeader.findExtension(),
			// 		httpRequestHeader.getPath());
			// 	response200Header(dos, httpRequestHeader.findExtension(), responseBody.length);
			// } else {
			// 	if (httpRequestHeader.hasEqualPathWith("/user/create")) {
			// 		Map<String, String> params = httpRequestBody.getRequestParams();
			// 		User newUser = new User(params.get("userId"), params.get("password"), params.get("name"),
			// 			params.get("email"));
			//
			// 		response302Header(dos, "/index.html");
			// 	}
			// }

			// responseBody(dos, responseBody);
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

}
