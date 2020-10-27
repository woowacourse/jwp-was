package web.server.domain.response;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ResponseCookies {

    private final Set<ResponseCookie> responseCookies;

    public ResponseCookies() {
        this.responseCookies = new HashSet<>();
    }

    public void addCookie(ResponseCookie responseCookie) {
        responseCookies.add(responseCookie);
    }

    public Set<ResponseCookie> getResponseCookies() {
        return Collections.unmodifiableSet(responseCookies);
    }
}
