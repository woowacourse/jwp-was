package webserver.http.request;

import webserver.http.request.header.HttpRequestHttpHeaders;

public class HttpRequest {

	private final HttpRequestStartLine httpRequestStartLine;
	private final HttpRequestHttpHeaders httpRequestHttpHeaders;
	private final HttpRequestBody httpRequestBody;

	public HttpRequest(HttpRequestStartLine httpRequestStartLine,
		HttpRequestHttpHeaders httpRequestHttpHeaders, HttpRequestBody httpRequestBody) {
		this.httpRequestStartLine = httpRequestStartLine;
		this.httpRequestHttpHeaders = httpRequestHttpHeaders;
		this.httpRequestBody = httpRequestBody;
	}

	public boolean isSameUrl(String url) {
		return httpRequestStartLine.isSameUrl(url);
	}

	public HttpRequestBody getHttpBody() {
		return httpRequestBody;
	}

	public String getHttpMethod() {
		return this.httpRequestStartLine.getHttpMethod();
	}

	public String getRequestTarget() {
		return this.httpRequestStartLine.getRequestTarget();
	}
}
