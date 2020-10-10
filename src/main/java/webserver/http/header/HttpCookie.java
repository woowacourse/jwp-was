package webserver.http.header;

import exception.InvalidCookieException;
import utils.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpCookie {
    private static final String COOKIE_DELIMITER = ";";

    private final Map<String, String> cookies;

    private HttpCookie(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public String toHttpMessage() {
        return this.cookies.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining(COOKIE_DELIMITER));
    }

    public static class Builder {
        private final Map<String, String> cookies = new LinkedHashMap<>();

        public Builder addCookie(String name, String value) {
            StringUtils.validateNonNullAndNotEmpty(() -> new InvalidCookieException(name, value));

            String cookieName = name.trim();
            String cookieValue = value.trim();
            if (cookieName.isEmpty() || cookieValue.isEmpty()) {
                throw new InvalidCookieException(name, value);
            }

            this.cookies.put(cookieName, cookieValue);

            return this;
        }

        public HttpCookie build() {
            return new HttpCookie(this.cookies);
        }
    }
}
