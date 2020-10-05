package web.server.domain.response;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Cookies {

    private final Set<Cookie> cookies;

    public Cookies() {
        this.cookies = new HashSet<>();
    }

    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    public Set<Cookie> getCookies() {
        return Collections.unmodifiableSet(cookies);
    }

}
