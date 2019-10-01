package http.common;

import com.google.common.base.Strings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class HttpCookie {
    public static final String COOKIE_DELIMITER = "; ";
    public static final String COOKIE_VALUE_DELIMITER = "=";
    private static final String CRLF = "\r\n";

    private final Map<String, String> cookies;
    private String path;

    private HttpCookie(Map<String, String> cookie) {
        this.cookies = cookie;
        path = "/";
    }

    public static HttpCookie of(String cookieString) {
        if (Strings.isNullOrEmpty(cookieString)) {
            return of();
        }

        return new HttpCookie(
                Arrays.stream(cookieString.split(COOKIE_DELIMITER))
                        .map(cookie -> cookie.split(COOKIE_VALUE_DELIMITER))
                        .collect(Collectors.toMap(token -> token[0], token -> token[1]))
        );
    }

    public static HttpCookie of() {
        return new HttpCookie(new HashMap<>());
    }

    public void put(String name, String value) {
        cookies.put(name, value);
    }

    public String getCookie(String name) {
        return cookies.get(name);
    }

    public Set<String> keySet() {
        return cookies.keySet();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (keySet().size() > 0) {
            sb.append(cookies.entrySet().stream()
                    .map(entry -> entry.getKey() + COOKIE_VALUE_DELIMITER + entry.getValue())
                    .collect(Collectors.joining(COOKIE_DELIMITER))
            );
            sb.append(String.format("%sPath%s%s", COOKIE_DELIMITER, COOKIE_VALUE_DELIMITER, path)).append(CRLF);
        }

        return sb.toString();
    }
}