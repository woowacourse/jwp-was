package webserver.http.response.content.type;

public enum ContentType {

	HTML("text/html;charset=utf-8"),
	CSS("text/css");

	private final String value;

	ContentType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
