package http;

import http.exception.InvalidCookieAddException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Cookie {
    public static final String HTTP_ONLY = "HttpOnly";
    public static final String SECURE = "Secure";
    private static final String COOKIE_NAME_VALUE_DELIMITER = "=";
    private static final String COOKIE_DELIMITER = "; ";

    private Map<String, String> cookies = new HashMap<>();
    private boolean httpOnly = false;
    private boolean secure = false;

    public Cookie() {
    }

    public Cookie(String cookieQuery) {
        cookies = parseCookie(cookieQuery);
    }

    public Cookie(String name, String value) {
        cookies.put(name, value);
    }

    public void addCookie(String name, String value) {
        cookies.put(name, value);
    }

    public void addCookie(String name) {
        if (HTTP_ONLY.equals(name)) {
            httpOnly = true;
            return;
        }
        if (SECURE.equals(name)) {
            secure = true;
            return;
        }
        throw new InvalidCookieAddException();
    }

    private Map<String, String> parseCookie(String cookieQuery) {
        return Arrays.stream(cookieQuery.split(COOKIE_DELIMITER))
                .map(query -> query.split(COOKIE_NAME_VALUE_DELIMITER))
                .collect(Collectors.toMap(q -> q[0].trim(), q -> q[1].trim()))
                ;
    }

    String convertCookieToString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> nameValueEntry : cookies.entrySet()) {
            sb.append(nameValueEntry.getKey())
                    .append(COOKIE_NAME_VALUE_DELIMITER)
                    .append(nameValueEntry.getValue())
                    .append(COOKIE_DELIMITER);
        }
        if (httpOnly) {
            sb.append(HTTP_ONLY + COOKIE_DELIMITER);
        }
        if (secure) {
            sb.append(SECURE + COOKIE_DELIMITER);
        }
        sb.append("Path=/");
        return sb.toString();
    }

    public String get(String name) {
        return cookies.get(name);
    }
}
