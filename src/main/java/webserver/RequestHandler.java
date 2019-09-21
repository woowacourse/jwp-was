package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import db.DataBase;
import method.HttpMethod;
import model.Request;
import model.Response;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

			Request request = new Request(RequestHeaderReader.readRequest(bufferedReader));
			String path = ResourcePathUtils.getResourcePath(request.getRequestElement("Path"));

			if (HttpMethod.POST.isSameMethod(request.getRequestElement("Method"))) {
				String body = RequestBodyReader.readRequestBody(bufferedReader, request.getRequestElement("Content-Length"));
				RequestBody requestBody = new RequestBody(QueryStringSeparator.separate(body));
				saveUser(requestBody.getBody());
				return;
			}

			byte[] responseBody = FileIoUtils.loadFileFromClasspath(path);
			if (path.contains("?")) {
				saveUser(QueryStringSeparator.separate(request.getRequestElement("Path")));
				return;
			}

			Response response = new Response(ResponseGenerator.responseHeader(path, responseBody.length));
			sendResponseHeader(response.getAllHeaders(), dos);
			sendResponseBody(responseBody, dos);
		} catch (IOException | URISyntaxException e) {
			logger.error(e.getMessage());
		}
	}

	private void saveUser(Map<String, String> userInfo) {
		User user = new User(userInfo.get("userId"), userInfo.get("password"),
				userInfo.get("name"), userInfo.get("email"));
		DataBase.addUser(user);
	}

	private void sendResponseHeader(List<String> responseHeader, DataOutputStream dos) throws IOException {
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
