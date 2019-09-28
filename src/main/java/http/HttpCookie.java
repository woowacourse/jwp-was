package http;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpCookie {
    private static final String HEADER_DELIMITER = ";";
    private static final String COOKIE_DELIMITER = "=";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final int KEY_VALUE = 2;

    private Map<String, String> cookies;

    private HttpCookie(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public static HttpCookie of(String header) {
        return header == null
                ? new HttpCookie(new HashMap<>())
                : new HttpCookie(parse(header));
    }

    private static Map<String, String> parse(String header) {
        Map<String, String> cookies = new HashMap<>();
        String[] spliced = header.split(HEADER_DELIMITER);
        Arrays.stream(spliced)
                .map(cookie -> cookie.split(COOKIE_DELIMITER))
                .filter(cookie -> cookie.length == KEY_VALUE)
                .forEach(cookie -> cookies.put(cookie[KEY_INDEX].trim(), cookie[VALUE_INDEX].trim()));
        return cookies;
    }

    public String get(String key) {
        return cookies.get(key);
    }
}
