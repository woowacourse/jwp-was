package http;

import exception.NotFoundHeaderException;

public enum HeaderElement {
	CONTENT_TYPE("Content-Type"),
	CONTENT_LENGTH("Content-Length"),
	LOCATION("Location"),
	PATH("Path"),
	HOST("Host"),
	CONNECTION("Connection"),
	ACCEPT("Accept"),
	CACHE_CONTROL("Cache-Control"),
	UPGRADE_INSECURE_REQUESTS("Upgrade-Insecure-Requests"),
	USER_AGENT("User-Agent"),
	SEC_FETCH_MODE("Sec-Fetch-Mode"),
	SEC_FETCH_SITE("Sec-Fetch-Site"),
	SEC_FETCH_USER("Sec-Fetch-User"),
	REFERER("Referer"),
	ACCEPT_ENCODING("Accept-Encoding"),
	ACCEPT_LANGUAGE("Accept-Language"),
	COOKIE("Cookie"),
	SET_COOKIE("Set-Cookie"),
	ORIGIN("Origin"),
	PRAGMA("Pragma"),
	PURPOSE("Purpose");

	private final String element;

	HeaderElement(String element) {
		this.element = element;
	}

	public String getElement() {
		return this.element;
	}

	public static HeaderElement getHeader(String element) {
		for (HeaderElement headerElement : HeaderElement.values()) {
			if (headerElement.getElement().equals(element)) {
				return headerElement;
			}
		}
		throw new NotFoundHeaderException();
	}
}
