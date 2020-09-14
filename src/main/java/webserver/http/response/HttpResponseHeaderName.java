package webserver.http.response;

public enum HttpResponseHeaderName {

	CONTENT_TYPE("Content-Type"),
	CONTENT_LENGTH("Content-Length"),
	LOCATION("Location");

	private final String value;

	HttpResponseHeaderName(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
