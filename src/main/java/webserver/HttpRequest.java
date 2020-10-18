package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import utils.IOUtils;

public class HttpRequest {
	private static final String EMPTY = "";

	private final RequestHeaders requestHeaders;
	private final String body;

	public HttpRequest(InputStream in) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		requestHeaders = RequestHeaders.of(br);
		this.body = extractBody(br);
	}

	private String extractBody(BufferedReader br) throws IOException {
		if (requestHeaders.isPostMethod()) {
			return IOUtils.readData(br, requestHeaders.getContentSize());
		}
		return EMPTY;
	}

	public String getBody() {
		return body;
	}

	public String getMethod() {
		return requestHeaders.getParameter("method");
	}

	public String getUrl() {
		return requestHeaders.getParameter("url");
	}

	public String getParameter(String param) {
		return requestHeaders.getParameter(param);
	}
}
