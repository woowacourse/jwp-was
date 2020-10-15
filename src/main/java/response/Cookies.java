package response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Cookies {

    public static Cookies EMPTY_COOKIES = new Cookies();

    private final List<Cookie> cookies;

    private Cookies() {
        cookies = new ArrayList<>();
    }

    public static Cookies createWithSingleCookie(String cookieName, String cookieValue,
        String path) {
        return new Cookies(Collections.singletonList(new Cookie(cookieName, cookieValue, path)));
    }

    public Cookies(List<Cookie> cookies) {
        validateIfDuplicatedCookieNameExists(cookies);
        this.cookies = Collections.unmodifiableList(cookies);
    }

    private void validateIfDuplicatedCookieNameExists(List<Cookie> cookies) {
        Map<String, String> cookieNameValueMap = new HashMap<>();

        for (Cookie cookie : cookies) {
            cookieNameValueMap.put(cookie.getName(), cookie.getValue());
        }
        if (cookieNameValueMap.size() != cookies.size()) {
            throw new IllegalArgumentException("cookies with same name exist.");
        }
    }

    public List<String> toCookieHeaderValueFormats() {
        List<String> cookieValues = cookies.stream()
            .map(Cookie::toHttpResponseHeaderValueFormat)
            .collect(Collectors.toList());
        return Collections.unmodifiableList(cookieValues);
    }

    public boolean isNotEmpty() {
        return !this.cookies.isEmpty();
    }
}
