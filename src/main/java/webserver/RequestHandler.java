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
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestBody;
import webserver.http.request.HttpRequestStartLine;
import webserver.http.request.header.HttpRequestHttpHeaders;
import webserver.http.response.HttpResponse;
import webserver.http.response.utils.HttpResponseConverter;
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
			HttpResponse httpResponse = HttpProcessor.process(httpRequest);

			dos.write(HttpResponseConverter.convert(httpResponse));
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private HttpRequest parseHttpRequest(InputStream in) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		String headers = printHeader(bufferedReader);
		HttpRequestStartLine httpRequestStartLine = HeaderIOUtils.parseStartLine(headers);
		HttpRequestHttpHeaders httpRequestHttpHeaders = HeaderIOUtils.parseHttpHeaders(headers);
		HttpRequestBody httpRequestBody = new HttpRequestBody(
			BodyIOUtils.parseHttpBody(httpRequestStartLine, httpRequestHttpHeaders, bufferedReader));
		return new HttpRequest(httpRequestStartLine, httpRequestHttpHeaders, httpRequestBody);
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
}
