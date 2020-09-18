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
import utils.IOUtils;
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

			String httpMethod = requestHeader.split(" ")[0];
			String request = requestHeader.split(" ")[1];
			DataOutputStream dos = new DataOutputStream(out);

			int contextLength = 0;

			while (!"".equals(requestHeader)) {
				requestHeader = br.readLine();
				if (requestHeader.contains("Content-Length")) {
					contextLength = Integer.parseInt(requestHeader.split( " ")[1]);
				}
				logger.debug("header : {}", requestHeader);
			}

			if ("GET".equals(httpMethod) && "/user/create".equals(request)) {
				String requestParameter = request.split("\\?")[1];
				signUpUser(dos, requestParameter);
			} else if("POST".equals(httpMethod) && "/user/create".equals(request)) {
				String body = IOUtils.readData(br, contextLength);
				logger.debug("body : {}", body);
				signUpUser(dos, body);
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

	private void signUpUser(DataOutputStream dos, String requestParameter) {
		Map<String, String> requestParameters = RequestPathUtil.extractSignUpRequestData(requestParameter);
		User user = new User(requestParameters.get("userId"), requestParameters.get("password"),
			requestParameters.get("name"), requestParameters.get("email"));
		DataBase.addUser(user);

		logger.debug("Database : {}", user);
		response200Header(dos, 0);
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
