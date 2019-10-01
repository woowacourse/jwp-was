package webserver.http.session;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class Cookie {
    private static final String JSESSIONID = "JSESSIONID";
    private static final String COOKIE_LINE_REGEX = "; ";
    private static final String COOKIE_REGEX = "=";
    private static final String NONE_VALUE = "";

    private final Map<String, String> cookies;
    private final HttpSession httpSession;

    public Cookie() {
        cookies = new HashMap<>();
        this.httpSession = SessionManager.getSession(UUID.randomUUID().toString());
    }

    public Cookie(String cookieLine) {
        this.cookies = parseCookies(cookieLine);
        this.httpSession = checkSession();
    }

    private Map<String, String> parseCookies(String cookieLine) {
        return StringUtils.isEmpty(cookieLine) ?
                new HashMap<>() : extractCookies(cookieLine.split(COOKIE_LINE_REGEX));
    }

    private Map<String, String> extractCookies(String[] attribute) {
        return Arrays.stream(attribute)
                .map(cookie -> cookie.split(COOKIE_REGEX))
                .collect(Collectors.toMap(index -> index[0],
                        index -> index.length > 1 ? index[1] : NONE_VALUE));
    }

    private HttpSession checkSession() {
        return hasSession() ?
                SessionManager.getSession(cookies.get(JSESSIONID)) :
                SessionManager.getSession(UUID.randomUUID().toString());
    }

    private boolean hasSession() {
        return cookies.entrySet().stream()
                .anyMatch(map -> map.getKey().equals(JSESSIONID));
    }

    public String getCookies(String key) {
        return cookies.getOrDefault(key, null);
    }

    public HttpSession getHttpSession() {
        return httpSession;
    }
}