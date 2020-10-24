package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import utils.IOUtils;

public class HttpRequest {
	private static final String EMPTY = "";

	private final RequestLine requestLine;
	private final RequestHeaders requestHeaders;
	private final String body;

	public HttpRequest(InputStream in) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		requestLine = RequestLine.of(br);
		requestHeaders = RequestHeaders.of(br);
		this.body = extractBody(br);
	}

	private String extractBody(BufferedReader br) throws IOException {
		if (requestLine.isPostMethod()) {
			return IOUtils.readData(br, requestHeaders.getContentSize());
		}
		return EMPTY;
	}

	public String getBody() {
		return body;
	}

	public String getMethod() {
		return requestLine.getMethod();
	}

	public String getUrl() {
		return requestLine.getUrl();
	}

	public String getHeader(String header) {
		return requestHeaders.getHeader(header);
	}

	public String getParameter(String param) {
		return requestLine.getParameter(param);
	}
}
