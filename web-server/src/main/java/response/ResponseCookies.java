package response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResponseCookies {

    public static ResponseCookies EMPTY_COOKIES = new ResponseCookies();

    private final List<ResponseCookie> cookies;

    private ResponseCookies() {
        cookies = new ArrayList<>();
    }

    public static ResponseCookies createWithSingleCookie(
            String cookieName, String cookieValue, String path) {
        return new ResponseCookies(
            Collections.singletonList(new ResponseCookie(cookieName, cookieValue, path)));
    }

    public ResponseCookies(List<ResponseCookie> cookies) {
        validateIfDuplicatedCookieNameExists(cookies);
        this.cookies = Collections.unmodifiableList(cookies);
    }

    private void validateIfDuplicatedCookieNameExists(List<ResponseCookie> cookies) {
        Map<String, String> cookieNameValueMap = new HashMap<>();

        for (ResponseCookie cookie : cookies) {
            cookieNameValueMap.put(cookie.getName(), cookie.getValue());
        }
        if (cookieNameValueMap.size() != cookies.size()) {
            throw new IllegalArgumentException("cookies with same name exist.");
        }
    }

    public List<String> toCookieHeaderValueFormats() {
        List<String> cookieValues = cookies.stream()
            .map(ResponseCookie::toHttpResponseHeaderValueFormat)
            .collect(Collectors.toList());
        return Collections.unmodifiableList(cookieValues);
    }

    public boolean isNotEmpty() {
        return !this.cookies.isEmpty();
    }
}
