package webserver.http;

public enum HttpVersion {
	HTTP11("HTTP/1.1");

	private String version;

	HttpVersion(final String version) {
		this.version = version;
	}

	public String getVersion() {
		return this.version;
	}
}
