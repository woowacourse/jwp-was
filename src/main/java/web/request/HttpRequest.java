package web.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpRequest {
	private final RequestLine requestLine;
	private final RequestHeader requestHeader;
	private final Parameter parameter;

	public HttpRequest(RequestLine requestLine, RequestHeader requestHeader, Parameter parameter) {
		this.requestLine = requestLine;
		this.requestHeader = requestHeader;
		this.parameter = parameter;
	}

	public static HttpRequest of(InputStream in) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

		String requestFirstLine = br.readLine();

		RequestLine requestLine = RequestLine.of(requestFirstLine);
		RequestHeader requestHeader = RequestHeader.of(br);
		Parameter parameter = Parameter.of(br, requestLine, requestHeader);

		return new HttpRequest(requestLine, requestHeader, parameter);
	}

	public HttpMethod getMethod() {
		return requestLine.getMethod();
	}

	public String getPath() {
		return requestLine.getPath();
	}

	public String getHeader(String key) {
		return requestHeader.get(key);
	}

	public String getParameter(String key) {
		return parameter.get(key);
	}
}
