package http.request;

import java.util.Map;
import java.util.Objects;

import http.Header;

public class HttpRequest {
	private HttpRequestHeader httpRequestHeader;
	private HttpRequestBody httpRequestBody;

	public HttpRequest(HttpRequestHeader httpRequestHeader) {
		this.httpRequestHeader = httpRequestHeader;
	}

	public HttpRequest(HttpRequestHeader httpRequestHeader, HttpRequestBody httpRequestBody) {
		this.httpRequestHeader = httpRequestHeader;
		this.httpRequestBody = httpRequestBody;
	}

	public String getRequestHeaderElement(Header attribute) {
		return httpRequestHeader.getRequestElement(attribute);
	}

	public Map<String, String> getHttpRequestBody() {
		if (Objects.isNull(httpRequestBody)) {
			throw new NullPointerException();
		}
		return httpRequestBody.getBody();
	}
}
