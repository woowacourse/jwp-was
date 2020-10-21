package web;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {
	private final RequestLine requestLine;
	private final Header header;
	private final Parameter parameter;

	public HttpRequest(RequestLine requestLine, Header header, Parameter parameter) {
		this.requestLine = requestLine;
		this.header = header;
		this.parameter = parameter;
	}

	public static HttpRequest of(BufferedReader br) throws IOException {
		String requestFirstLine = br.readLine();

		RequestLine requestLine = RequestLine.of(requestFirstLine);

		Header header = Header.of(br);

		Parameter parameter = Parameter.of(br, requestLine, header);

		return new HttpRequest(requestLine, header, parameter);
	}

	public HttpMethod getMethod() {
		return requestLine.getMethod();
	}

	public String getPath() {
		return requestLine.getPath();
	}

	public String getHeader(String key) {
		return header.get(key);
	}

	public String getParameter(String key) {
		return parameter.get(key);
	}
}
