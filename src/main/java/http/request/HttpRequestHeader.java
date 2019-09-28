package http.request;

import java.util.Map;
import java.util.Objects;

import exception.NotFoundElementException;
import http.HeaderElement;

public class HttpRequestHeader {
	private final Map<HeaderElement, String> header;

	public HttpRequestHeader(final Map<HeaderElement, String> header) {
		this.header = header;
	}

	public String getRequestElement(HeaderElement attribute) {
		String value = header.get(attribute);
		if (Objects.nonNull(value)) {
			return value;
		}
		throw new NotFoundElementException();
	}
}
