package webserver.http.response;

public class HttpStatusLine {

	public static final String DEFAULT_HTTP_VERSION = "HTTP/1.1";

	private final String httpVersion;
	private final StatusCode statusCode;

	public HttpStatusLine(StatusCode statusCode) {
		this.httpVersion = DEFAULT_HTTP_VERSION;
		this.statusCode = statusCode;
	}

	public static String getDefaultHttpVersion() {
		return DEFAULT_HTTP_VERSION;
	}

	public String getHttpVersion() {
		return httpVersion;
	}

	public StatusCode getStatusCode() {
		return statusCode;
	}
}
