package webserver;

import java.util.Map;

import model.RequestHeader;

public class HttpRequest {
	private RequestHeader requestHeader;
	private RequestBody requestBody;

	public HttpRequest(RequestHeader requestHeader) {
		this.requestHeader = requestHeader;
	}

	public HttpRequest(RequestHeader requestHeader, RequestBody requestBody) {
		this.requestHeader = requestHeader;
		this.requestBody = requestBody;
	}

	public String getRequestHeaderElement(String key) {
		return requestHeader.getRequestElement(key);
	}

	public Map<String, String> getRequestBody() {
		return requestBody.getBody();
	}
}
