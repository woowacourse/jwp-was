package webserver.http.response;

public class HttpResponse {

	private final HttpStatusLine httpStatusLine;
	private final HttpResponseHeaders httpResponseHeaders;
	private final HttpResponseBody httpResponseBody;

	public HttpResponse(HttpStatusLine httpStatusLine, HttpResponseHeaders httpResponseHeaders,
		HttpResponseBody httpResponseBody) {
		this.httpStatusLine = httpStatusLine;
		this.httpResponseHeaders = httpResponseHeaders;
		this.httpResponseBody = httpResponseBody;
	}

	public HttpStatusLine getHttpStatusLine() {
		return httpStatusLine;
	}

	public HttpResponseHeaders getHttpResponseHeaders() {
		return httpResponseHeaders;
	}

	public HttpResponseBody getHttpRequestBody() {
		return httpResponseBody;
	}
}
