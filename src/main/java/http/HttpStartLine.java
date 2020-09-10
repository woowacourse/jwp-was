package http;

public class HttpStartLine {
	private static final String START_LINE_DELIMITER = " ";

	private String method;
	private String url;
	private String version;

	private HttpStartLine(String method, String url, String version) {
		this.method = method;
		this.url = url;
		this.version = version;
	}

	public HttpStartLine(String startLine) {
		String[] splitedStartLine = startLine.split(START_LINE_DELIMITER);
		this.method = splitedStartLine[0];
		this.url = splitedStartLine[1];
		this.version = splitedStartLine[2];
	}

	public String getUrl() {
		return url;
	}
}
