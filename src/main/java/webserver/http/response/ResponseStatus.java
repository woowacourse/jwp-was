package webserver.http.response;

public enum ResponseStatus {
	OK("OK", "200"),
	FOUND("Found", "302");

	private final String phrase;
	private final String code;

	ResponseStatus(final String phrase, final String code) {
		this.phrase = phrase;
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public String getPhrase() {
		return this.phrase;
	}
}
