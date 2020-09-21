package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.handler.Handler;
import webserver.handler.HandlerStorage;

public class RequestHandler implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
	private static final String DELIMITER = " ";

	private Socket connection;

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
	}

	public void run() {
		logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
			connection.getPort());

		try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String startLine = br.readLine();
			String path = startLine.split(DELIMITER)[1];
			DataOutputStream dos = new DataOutputStream(out);
			findBody(br);

			Handler handler = HandlerStorage.findHandler(path);
			handler.handleRequest(path, dos, br);
		} catch (IOException | URISyntaxException e) {
			logger.error(e.getMessage());
		}
	}

	private void findBody(BufferedReader br) throws IOException {
		String line = br.readLine();
		while (!"".equals(line)) {
			if (line == null) {
				break;
			}
			line = br.readLine();
		}
	}
}
