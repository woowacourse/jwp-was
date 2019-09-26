package http.request;

import java.util.Map;
import java.util.Objects;

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

	public String getRequestHeaderElement(String key) {
		return httpRequestHeader.getRequestElement(key);
	}

	public Map<String, String> getHttpRequestBody() {
		if (Objects.isNull(httpRequestBody)) {
			throw new NullPointerException();
		}
		return httpRequestBody.getBody();
	}
}
