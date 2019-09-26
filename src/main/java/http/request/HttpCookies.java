package http.request;

import com.google.common.collect.Lists;
import http.HttpCookie;

import java.util.List;

public class HttpCookies {
    private List<HttpCookie> cookies = Lists.newArrayList();

    public HttpCookies() {
    }

    public HttpCookies(String line) {
        for (String nameValue : line.split("; ")) {
            cookies.add(new HttpCookie(nameValue));
        }
    }

    public void addCookie(HttpCookie cookie) {
        cookies.add(cookie);
    }

    public String findValueBy(String name) {
        return cookies.stream()
                .filter(cookie -> cookie.checkName(name))
                .findFirst().orElse(HttpCookie.emptyInstance()).getValue();
    }

    public List<HttpCookie> getCookies() {
        return cookies;
    }
}
