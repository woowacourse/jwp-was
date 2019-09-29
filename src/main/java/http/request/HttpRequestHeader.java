package http.request;

import java.util.Map;
import java.util.Objects;

import http.HeaderElement;

public class HttpRequestHeader {
	private final Map<HeaderElement, String> header;

	public HttpRequestHeader(final Map<HeaderElement, String> header) {
		this.header = header;
	}

	public String getRequestElement(HeaderElement attribute) {
		return header.get(attribute);
	}

	public boolean isCookieValue(String cookieKey) {
		String cookie = getRequestElement(HeaderElement.COOKIE);
		if(Objects.nonNull(cookie) && cookie.contains(cookieKey)) {
			return true;
		}
		return false;
	}
}
