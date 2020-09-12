package webserver.http;

public class HttpRequest {

	private final HttpHeaders httpHeaders;
	private final HttpBody httpBody;

	public HttpRequest(HttpHeaders httpHeaders, HttpBody httpBody) {
		this.httpHeaders = httpHeaders;
		this.httpBody = httpBody;
	}

	public boolean isSameUrl(String url) {
		return httpHeaders.isSameUrl(url);
	}

	public boolean containsInUrl(String url) {
		return httpHeaders.containsInUrl(url);
	}

	public HttpHeaders getHttpHeaders() {
		return httpHeaders;
	}

	public HttpBody getHttpBody() {
		return httpBody;
	}

	public String getHttpMethod() {
		return this.httpHeaders.getHttpMethod();
	}

	public String getUrl() {
		return this.httpHeaders.getUrl();
	}
}
