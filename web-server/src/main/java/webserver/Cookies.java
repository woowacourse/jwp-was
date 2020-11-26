package webserver;

import java.util.List;
import java.util.Optional;

public class Cookies {

    private final List<Cookie> cookies;

    public Cookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }

    public Optional<Cookie> getCookie(String name) {
        return cookies.stream()
            .filter(cookie -> cookie.isSameName(name))
            .findFirst();
    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    public void add(Cookie cookie) {
        cookies.add(cookie);
    }
}
