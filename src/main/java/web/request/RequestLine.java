package web.request;

public class RequestLine {
	private static final String SPACE_DELIMITER = " ";

	private HttpMethod method;
	private String path;
	private String httpVersion = "HTTP/1.1";

	public RequestLine(HttpMethod method, String path) {
		this.method = method;
		this.path = path;
	}

	public static RequestLine of(String requestLine) {
		String[] requestLines = requestLine.split(SPACE_DELIMITER);
		HttpMethod method = HttpMethod.valueOf(requestLines[0]);
		String path = requestLines[1];

		return new RequestLine(method, path);
	}


	public HttpMethod getMethod() {
		return method;
	}

	public String getPath() {
		return path;
	}

	public String getHttpVersion() {
		return httpVersion;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
