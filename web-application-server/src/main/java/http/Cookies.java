package http;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Cookies {
    private static final String COOKIE_SPLITTER = "; ";
    public static final String KEY_VALUE_SPLITTER = "=";
    public static final int KEY_INDEX = 0;
    public static final int VALUE_INDEX = 1;

    private List<Cookie> cookies;

    public static Cookies of(String input) {
        if (Objects.isNull(input) || input.isEmpty()) {
            return new Cookies();
        }
        List<Cookie> cookies = Arrays.stream(input.split(COOKIE_SPLITTER))
                .map(value -> {
                    String[] keyValue = value.split(KEY_VALUE_SPLITTER);
                    return Cookie.of(keyValue[KEY_INDEX], keyValue[VALUE_INDEX]);
                }).collect(toList());
        return new Cookies(cookies);
    }

    public static Cookies empty() {
        return new Cookies();
    }

    private Cookies() {
        this.cookies = new ArrayList<>();
    }

    private Cookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }

    public String getSessionId() {
        Cookie cookie = cookies.stream()
                .filter(Cookie::isSessionId)
                .findFirst()
                .orElse(Cookie.createSessionIdCookie("new"));
        return cookie.getValue();
    }

    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    public List<String> toResponse() {
        return cookies.stream()
                .map(cookie -> cookie.getName() + KEY_VALUE_SPLITTER + cookie.getValue() + COOKIE_SPLITTER
                        + "Path=" + cookie.getPath())
                .collect(Collectors.toList());
    }
}
