package web.request;

import exception.RequestParameterNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Cookies {
    private static final String COOKIES_DELIMITER = "; ";
    private static final String COOKIE_DELIMITER = "=";

    private final Map<String, String> cookieMatcher = new HashMap<>();

    public Cookies(String cookies) {
        if (Objects.isNull(cookies) || cookies.isEmpty()) {
            return;
        }
        String[] tokens = cookies.split(COOKIES_DELIMITER);
        for (String token : tokens) {
            String[] value = token.split(COOKIE_DELIMITER);
            this.cookieMatcher.put(value[0], value[1]);
        }
    }

    public String getCookieByKey(String key) {
        if (cookieMatcher.containsKey(key)) {
            return cookieMatcher.get(key);
        }
        throw new RequestParameterNotFoundException("[COOKIE]" + key);
    }
}
