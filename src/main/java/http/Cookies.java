package http;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import http.request.HttpRequest;

public class Cookies {

    private static final String COOKIE = "Cookie";
    private static final String EQUAL = "=";
    private static final String SEMICOLON = ";";
    private static final String BLANK = " ";

    private final Map<String, Object> cookies;

    public Cookies(final HttpRequest httpRequest) {
        this.cookies = init(httpRequest);
    }

    private Map<String, Object> init(final HttpRequest httpRequest) {
        String cookieData = httpRequest.getHttpRequestHeaderByName(COOKIE);
        if (Objects.isNull(cookieData)) {
            return new LinkedHashMap<>();
        }
        Map<String, Object> cookies = new LinkedHashMap<>();
        String[] flatCookie = cookieData.split(SEMICOLON);
        for (String cookie : flatCookie) {
            String[] data = cookie.split(EQUAL);
            cookies.put(data[0].trim(), data[1].trim());
        }
        return cookies;
    }

    public void addCookie(final String name, final Object value) {
        this.cookies.put(name, value);
    }

    public boolean isEmpty() {
        return cookies.isEmpty();
    }

    public String flat() {
        return this.cookies.entrySet().stream()
                .map(key -> key.getKey() + EQUAL + key.getValue())
                .collect(Collectors.joining(SEMICOLON + BLANK));
    }
}
