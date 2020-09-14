package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;
import utils.ExtractUtils;
import utils.FileIoUtils;

public class RequestHandler implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
	private static final String DELIMITER = " ";
	private static final String FILE_PATH_TEMPLATES = "./templates";
	private static final String FILE_PATH_STATIC = "./static";
	private static final String FILE_TYPE_HTML = ".html";
	private static final String FILE_TYPE_ICO = ".ico";
	private static final String REDIRECT_URL = "/index.html";

	private Socket connection;

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
	}

	public void run() {
		logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
			connection.getPort());

		try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = br.readLine();
			String path = line.split(DELIMITER)[1];
			DataOutputStream dos = new DataOutputStream(out);
			findBody(br, line);

			if (path.startsWith("/user/create")) {
				HttpBody httpBody = new HttpBody(br, 100);
				Map<String, String> userInfo = ExtractUtils.extractUserInfo(httpBody.getBody());
				User user = new User(userInfo.get("userId"), userInfo.get("password"), userInfo.get("name"),
					userInfo.get("email"));
				DataBase.addUser(user);
				ResponseHeader.response302Header(dos, REDIRECT_URL, logger);
			} else {
				byte[] body = FileIoUtils.loadFileFromClasspath(parseFilePath(path));

				ResponseHeader.response200Header(dos, body.length, logger);
				responseBody(dos, body);
			}
		} catch (IOException | URISyntaxException e) {
			logger.error(e.getMessage());
		}
	}

	private void findBody(BufferedReader br, String line) throws IOException {
		while (!"".equals(line)) {
			line = br.readLine();
			if (line == null) {
				break;
			}
		}
	}

	private String parseFilePath(String path) {
		if (path.endsWith(FILE_TYPE_HTML) || path.endsWith(FILE_TYPE_ICO)) {
			return FILE_PATH_TEMPLATES + path;
		}
		return FILE_PATH_STATIC + path;
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
