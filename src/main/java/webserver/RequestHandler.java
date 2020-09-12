package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.BodyIOUtils;
import utils.HeaderIOUtils;
import webserver.http.HttpBody;
import webserver.http.HttpHeaders;
import webserver.http.HttpRequest;
import webserver.process.HttpProcessor;

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
			HttpRequest httpRequest = parseHttpRequest(in);

			DataOutputStream dos = new DataOutputStream(out);
			byte[] body = HttpProcessor.process(httpRequest);
			response200Header(dos, body.length);
			responseBody(dos, body);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private HttpRequest parseHttpRequest(InputStream in) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		String headers = printHeader(bufferedReader);
		HttpHeaders httpHeaders = HeaderIOUtils.parseHttpHeaders(headers);
		HttpBody httpBody = new HttpBody(BodyIOUtils.parseHttpBody(httpHeaders, bufferedReader));
		return new HttpRequest(httpHeaders, httpBody);
	}

	private String printHeader(BufferedReader bufferedReader) throws IOException {
		System.out.println("request header start: ");
		String request = bufferedReader.readLine();
		StringBuilder stringBuilder = new StringBuilder();
		while (!request.equals("")) {
			stringBuilder.append(request).append("\n");
			request = bufferedReader.readLine();
		}
		System.out.println(stringBuilder.toString());
		return stringBuilder.toString();
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
