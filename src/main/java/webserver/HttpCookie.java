package webserver;

import java.util.HashMap;
import java.util.Map;

public class HttpCookie {
	private static final String COOKIE_DELIMITER = "; ";
	private static final String VALUE_DELIMITER = "=";

	private final Map<String, String> cookies;

	public HttpCookie(String allCookies) {
		Map<String, String> cookies = new HashMap<>();
		for (String cookie : allCookies.split(COOKIE_DELIMITER)) {
			String[] cookieSet = cookie.split(VALUE_DELIMITER);
			cookies.put(cookieSet[0], cookieSet[1]);
		}
		this.cookies = cookies;
	}

	public String getCookie(String cookieName) {
		return cookies.get(cookieName);
	}
}
