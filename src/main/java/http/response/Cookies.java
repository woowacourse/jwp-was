package http.response;

import com.google.common.collect.Maps;
import http.exception.NotFoundCookieException;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Cookies {
    private Map<String, Cookie> cookies = Maps.newHashMap();

    public void setCookie(String key, Cookie cookie) {
        cookies.put(key, cookie);
    }

    public Set<Map.Entry<String, Cookie>> getCookies() {
        return cookies.entrySet();
    }

    public Cookie getCookie(String key) {
        return Optional.ofNullable(cookies.get(key)).orElseThrow(NotFoundCookieException::new);
    }
}
