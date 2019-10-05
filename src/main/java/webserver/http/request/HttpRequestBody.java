package webserver.http.request;

import java.util.Collections;
import java.util.Map;

public class HttpRequestBody {
	private final Map<String, String> body;

	public HttpRequestBody(final Map<String, String> body) {
		this.body = body;
	}

	public Map<String, String> getBody() {
		return Collections.unmodifiableMap(body);
	}
}
