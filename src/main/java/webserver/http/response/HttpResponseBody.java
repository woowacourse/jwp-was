package webserver.http.response;

public class HttpResponseBody {

	private final String body;

	public HttpResponseBody(String body) {
		this.body = body;
	}

	public String getBody() {
		return body;
	}
}
