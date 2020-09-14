package webserver.http.request;

public class HttpRequest {

	private final HttpRequestHttpHeaders httpRequestHttpHeaders;
	private final HttpRequestBody httpRequestBody;

	public HttpRequest(HttpRequestHttpHeaders httpRequestHttpHeaders, HttpRequestBody httpRequestBody) {
		this.httpRequestHttpHeaders = httpRequestHttpHeaders;
		this.httpRequestBody = httpRequestBody;
	}

	public boolean isSameUrl(String url) {
		return httpRequestHttpHeaders.isSameUrl(url);
	}

	public boolean containsInUrl(String url) {
		return httpRequestHttpHeaders.containsInUrl(url);
	}

	public HttpRequestHttpHeaders getHttpHeaders() {
		return httpRequestHttpHeaders;
	}

	public HttpRequestBody getHttpBody() {
		return httpRequestBody;
	}

	public String getHttpMethod() {
		return this.httpRequestHttpHeaders.getHttpMethod();
	}

	public String getUrl() {
		return this.httpRequestHttpHeaders.getUrl();
	}
}
