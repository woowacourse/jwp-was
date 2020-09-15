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
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;
import utils.FileIoUtils;
import utils.RequestPathUtil;

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
			BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
			String requestHeader = br.readLine();
			logger.debug("request line : {}", requestHeader);

			String request = requestHeader.split(" ")[1];
			DataOutputStream dos = new DataOutputStream(out);

			while (!requestHeader.equals("")) {
				requestHeader = br.readLine();
				logger.debug("header : {}", requestHeader);
			}

			if (request.startsWith("/user/create")) {
				Map<String, String> requestParameters = RequestPathUtil.extractSignUpRequestData(request);
				User user = new User(requestParameters.get("userId"), requestParameters.get("password"),
					requestParameters.get("name"), requestParameters.get("email"));
				DataBase.addUser(user);

				logger.debug("Database : {}", user);
				response200Header(dos, 0);
			} else {
				String requestPath = RequestPathUtil.extractFilePath(request);
				byte[] body = FileIoUtils.loadFileFromClasspath(requestPath);

				response200Header(dos, body.length);
				responseBody(dos, body);
			}
		} catch (IOException | URISyntaxException e) {
			logger.error(e.getMessage());
		}
	}

	private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
		try {
			dos.writeBytes("HTTP/1.1 200 OK \r\n");
			dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
			dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
			dos.writeBytes("\r\n");
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
