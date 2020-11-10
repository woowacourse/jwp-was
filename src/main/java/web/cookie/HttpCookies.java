package web.cookie;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HttpCookies {
    private static final String COOKIE_DELIMITER = "; ";
    private static final String KEY_VALUE_DELIMITER = "=";

    private final List<HttpCookie> cookies;

    public HttpCookies() {
        this.cookies = new ArrayList<>();
    }

    public HttpCookies(String cookies) {
        this();
        if (StringUtils.isEmpty(cookies)) {
            return;
        }

        String[] cookieList = cookies.split(COOKIE_DELIMITER);

        for (String cookie : cookieList) {
            String[] entry = cookie.split(KEY_VALUE_DELIMITER);
            this.cookies.add(new HttpCookie(entry[0], entry[1]));
        }
    }

    public void add(String key, String value) {
        this.cookies.add(new HttpCookie(key, value));
    }

    public void add(String key, String value, CookieOption cookieOption) {
        HttpCookie httpCookie = new HttpCookie(key, value);
        httpCookie.addOption(cookieOption);
        this.cookies.add(httpCookie);
    }

    public void add(String key, String value, CookieOption cookieOption, String optionValue) {
        HttpCookie httpCookie = new HttpCookie(key, value);
        httpCookie.addOption(cookieOption, optionValue);
        this.cookies.add(httpCookie);
    }

    public List<HttpCookie> getCookies() {
        return Collections.unmodifiableList(cookies);
    }

    public String convertToResponse() {
        StringBuilder cookies = new StringBuilder();

        for (HttpCookie httpCookie : this.cookies) {
            cookies.append(httpCookie.write());
        }

        return cookies.toString();
    }

    public String get(String key) {
        return this.cookies.stream()
                .filter(cookie -> cookie.hasKey(key))
                .findFirst()
                .map(HttpCookie::getValue)
                .orElse(null);
    }
}
