package http.response;

public enum ResponseStatus {
	OK("OK", "200"),
	FOUND("Found", "302");

	private final String description;
	private final String code;

	ResponseStatus(final String description, final String code) {
		this.description = description;
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public String getDescription() {
		return this.description;
	}
}
