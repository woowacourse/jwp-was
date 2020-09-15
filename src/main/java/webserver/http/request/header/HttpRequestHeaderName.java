package webserver.http.request.header;

public enum HttpRequestHeaderName {
	CONTENT_LENGTH("Content-Length");

	private final String value;

	HttpRequestHeaderName(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
