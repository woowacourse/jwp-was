package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import db.DataBase;
import model.QueryString;
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

			if(path.contains("?")) {
				QueryString queryString = new QueryString(QueryStringSeparator.separate(request.getRequestElement("Path")));
				User user = new User(queryString.getQueryString("userId"), queryString.getQueryString("password"),
						queryString.getQueryString("name"), queryString.getQueryString("email"));
				DataBase.addUser(user);
				return;
			}

			byte[] body = FileIoUtils.loadFileFromClasspath(path);

			Response response = new Response(ResponseGenerator.responseHeader(path, body.length));
			sendResponseHeader(response.getAllHeaders(), dos);
			sendResponseBody(body, dos);
		} catch (IOException | URISyntaxException e) {
			logger.error(e.getMessage());
		}
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
