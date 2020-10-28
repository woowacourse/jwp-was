package domain.response;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import servlet.Cookie;

public class ResponseCookies {

    private final Set<ResponseCookie> responseCookies;

    public ResponseCookies() {
        this.responseCookies = new HashSet<>();
    }

    public void addCookie(Cookie cookie, String[] options) {
        new ResponseCookie(cookie, options);
    }

    public Set<ResponseCookie> getResponseCookies() {
        return Collections.unmodifiableSet(responseCookies);
    }
}
