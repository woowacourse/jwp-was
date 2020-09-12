package webserver.http;

public class HttpBody {

	public static final HttpBody EMPTY_BODY = new HttpBody("");

	private final String httpBody;

	public HttpBody(String httpBody) {
		this.httpBody = httpBody;
	}

	public String[] split(String regex) {
		return httpBody.split(regex);
	}

	public String getHttpBody() {
		return httpBody;
	}
}
