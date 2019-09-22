package http;

import java.util.Collections;
import java.util.Map;

public class RequestBody {
	private final Map<String, String> body;

	public RequestBody(final Map<String, String> body) {
		this.body = body;
	}

	public Map<String, String> getBody() {
		return Collections.unmodifiableMap(body);
	}
}
