package http;

import exception.NotFoundHeaderException;

public enum Header {
	PROTOCOL("HTTP"),
	PROTOCOL_VERSION("1.1"),
	CODE("Code"),
	DESCRIPTION("description"),
	CONTENT_TYPE("Content-Type"),
	CONTENT_LENGTH("Content-Length"),
	LOCATION("Location"),
	PATH("Path"),
	METHOD("Method"),
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
	PRAGMA("Pragma");

	private final String element;

	Header(String element) {
		this.element = element;
	}

	public String getElement() {
		return this.element;
	}

	public static Header getHeader(String element) {
		for (Header header : Header.values()) {
			if (header.getElement().equals(element)) {
				return header;
			}
		}
		throw new NotFoundHeaderException();
	}
}
