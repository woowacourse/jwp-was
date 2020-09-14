package webserver.http.request.header;

import java.util.Map;

public class HttpRequestHttpHeaders {

	private final Map<String, String> httpHeaders;

	public HttpRequestHttpHeaders(Map<String, String> httpHeaders) {
		this.httpHeaders = httpHeaders;
	}

	public int size() {
		return httpHeaders.size();
	}

	public String getByHeaderName(String headerName) {
		return httpHeaders.get(headerName);
	}
}
