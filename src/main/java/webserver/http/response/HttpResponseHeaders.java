package webserver.http.response;

import java.util.Map;

public class HttpResponseHeaders {

	private final Map<HttpResponseHeaderName, String> httpHeaders;

	public HttpResponseHeaders(Map<HttpResponseHeaderName, String> httpHeaders) {
		this.httpHeaders = httpHeaders;
	}

	public Map<HttpResponseHeaderName, String> getHttpHeaders() {
		return httpHeaders;
	}
}
