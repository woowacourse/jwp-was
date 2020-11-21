package request;

import cookie.Cookie;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestCookies {

    private static String REQUEST_COOKIES_SEPARATOR = "; ";

    private Map<String, Cookie> cookiesByName;

    private RequestCookies(Map<String, Cookie> cookiesByName) {
        this.cookiesByName = Collections.unmodifiableMap(cookiesByName);
    }

    public static RequestCookies from(String requestCookiesFormat) {
        List<Cookie> cookies = makeCookiesFrom(requestCookiesFormat);

        Map<String, Cookie> cookiesByName = new HashMap<>();

        for (Cookie cookie : cookies) {
            cookiesByName.put(cookie.getName(), cookie);
        }
        return new RequestCookies(cookiesByName);
    }

    private static List<Cookie> makeCookiesFrom(String requestCookiesFormat) {
        List<String> cookieFormats = Arrays.asList(
            requestCookiesFormat.split(REQUEST_COOKIES_SEPARATOR));

        return cookieFormats.stream()
            .map(Cookie::from)
            .collect(Collectors.toList());
    }

    public String getValue(String cookieName) {
        if (cookiesByName.containsKey(cookieName)) {
            return cookiesByName.get(cookieName).getValue();
        }
        throw new IllegalArgumentException("cookie name \"" + cookieName + "\" does not exist.");
    }
}
