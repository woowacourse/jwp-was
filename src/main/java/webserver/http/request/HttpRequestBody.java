package webserver.http.request;

public class HttpRequestBody {

	public static final HttpRequestBody EMPTY_BODY = new HttpRequestBody("");

	private final String httpBody;

	public HttpRequestBody(String httpBody) {
		this.httpBody = httpBody;
	}

	public String[] split(String regex) {
		return httpBody.split(regex);
	}

	public String getHttpBody() {
		return httpBody;
	}
}
