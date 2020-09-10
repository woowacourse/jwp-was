package webserver;

public class HttpMainHeader {

	private final HttpMethod httpMethod;
	private final String requestUrl;

	public HttpMainHeader(HttpMethod httpMethod, String requestUrl) {
		this.httpMethod = httpMethod;
		this.requestUrl = requestUrl;
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public String getRequestUrl() {
		return requestUrl;
	}
}
