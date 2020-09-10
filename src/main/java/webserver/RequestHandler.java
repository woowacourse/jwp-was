package webserver;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.HeaderIOUtils;

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
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

			printHeader(bufferedReader);
			DataOutputStream dos = new DataOutputStream(out);
			byte[] body = "Hello World".getBytes();
			response200Header(dos, body.length);
			responseBody(dos, body);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private void printHeader(BufferedReader bufferedReader) throws IOException {
		System.out.println("request header start: ");
		String request = bufferedReader.readLine();
		boolean isMainHeaders = false;
		String requestUrl = "";
		while (!request.equals("")) {
			if (isMainHeaders) {
				requestUrl = HeaderIOUtils.parseUrl(request);
				isMainHeaders = true;
			}
			System.out.println("header : " + request);
			request = bufferedReader.readLine();
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
