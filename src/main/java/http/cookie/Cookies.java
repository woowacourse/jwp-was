package http.cookie;

import java.util.Collections;
import java.util.List;

public class Cookies {
    private List<Cookie> cookies;

    public Cookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }

    public Cookie findCookie(String cookieName) {
        return cookies.stream()
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findFirst()
                .orElse(Cookie.builder().build());
    }

    public void add(Cookie cookie) {
        this.cookies.add(cookie);
    }

    public List<Cookie> getCookies() {
        return Collections.unmodifiableList(this.cookies);
    }
}
