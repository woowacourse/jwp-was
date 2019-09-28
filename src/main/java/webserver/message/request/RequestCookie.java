package webserver.message.request;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestCookie {
    private static final String ATTRIBUTE_DELIMITER = ";";
    private static final String COOKIE_KEY_VALUE_DELIMITER = "=";
    private static final String EMPTY = "";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private final Map<String, String> cookie;

    public RequestCookie(final String rawCookie) {
        this.cookie = (rawCookie == null) ? new HashMap<>() : makeCookie(rawCookie);
    }

    private Map<String, String> makeCookie(String cookie) {
        return Arrays.stream(cookie.split(ATTRIBUTE_DELIMITER))
                .map(query -> query.split(COOKIE_KEY_VALUE_DELIMITER, 2))
                .filter(this::queryFilter)
                .collect(Collectors.toUnmodifiableMap(array -> array[KEY_INDEX].trim(), array -> array[VALUE_INDEX].trim()));
    }

    private boolean queryFilter(final String[] keyValue) {
        return keyValue.length == 2 && !keyValue[KEY_INDEX].isEmpty() && !keyValue[VALUE_INDEX].isEmpty();
    }

    public Map<String, String> getCookie() {
        return cookie;
    }

    public String getCookieValue(final String key) {
        return this.cookie.getOrDefault(key, EMPTY);
    }
}
