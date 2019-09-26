package http.request;

import java.util.Map;
import java.util.Objects;

import exception.NotFoundElementException;
import http.Header;

public class HttpRequestHeader {
	private final Map<Header, String> header;

	public HttpRequestHeader(final Map<Header, String> header) {
		this.header = header;
	}

	public String getRequestElement(Header attribute) {
		String value = header.get(attribute);
		if (Objects.isNull(value)) {
			throw new NotFoundElementException();
		}
		return value;
	}
}
