package http.request;

import java.util.Map;
import java.util.Objects;

import exception.NotFoundElementException;

public class HttpRequestHeader {
	private final Map<String, String> header;

	public HttpRequestHeader(final Map<String, String> header) {
		this.header = header;
	}

	public String getRequestElement(String key) {
		String value = header.get(key);
		if (Objects.isNull(value)) {
			throw new NotFoundElementException();
		}
		return header.get(key);
	}
}
