package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import model.RequestHeader;
import model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import utils.*;

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
			DataOutputStream dos = new DataOutputStream(out);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

			RequestHeader requestHeader = new RequestHeader(RequestHeaderReader.readRequest(bufferedReader));

			if ("/user/create".equals(requestHeader.getRequestElement("Path"))) {
				String body = RequestBodyReader.readRequestBody(bufferedReader, requestHeader.getRequestElement("Content-Length"));
				RequestBody requestBody = new RequestBody(QueryStringSeparator.separate(body));
				UserService.saveUser(requestBody.getBody());
				Response response = new Response(ResponseGenerator.responseHeader("http://localhost:8080/index.html"));
				redirect(response, dos);
				return;
			}

			String path = ResourcePathUtils.getResourcePath(requestHeader.getRequestElement("Path"));
			if (path.contains("?")) {
				UserService.saveUser(QueryStringSeparator.separate(path.substring(path.indexOf("?") + 1)));
				return;
			}

			byte[] responseBody = FileIoUtils.loadFileFromClasspath(path);
			Response response = new Response(ResponseGenerator.responseHeader(path, responseBody.length));
			forward(response.getAllHeaders(), dos);
			sendResponseBody(responseBody, dos);
		} catch (IOException | URISyntaxException e) {
			logger.error(e.getMessage());
		}
	}

	private void redirect(Response response, DataOutputStream dos) throws IOException {
		dos.writeBytes(response.getRequestElement("Http") + " "
				+ response.getRequestElement("Code") + " "
				+ response.getRequestElement("Description") + "\r\n");
		dos.writeBytes("Location: " + response.getRequestElement("Location") + "\r\n");
		dos.writeBytes("\r\n");
		dos.flush();
	}

	private void forward(List<String> responseHeader, DataOutputStream dos) throws IOException {
		for (String header : responseHeader) {
			dos.writeBytes(header);
		}
		dos.writeBytes("\r\n");
	}

	private void sendResponseBody(byte[] body, DataOutputStream dos) {
		try {
			dos.write(body, 0, body.length);
			dos.flush();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}
