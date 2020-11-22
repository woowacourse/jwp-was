package kr.wootecat.dongle.http;

import static java.lang.String.*;
import static java.util.stream.Collectors.*;
import static utils.SplitUtils.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.wootecat.dongle.http.exception.IllegalRequestDataFormatException;

public class Cookies {
    private static final Pattern HTTP_COOKIE_VALUE_PATTERN = Pattern.compile(
            "[\\w-]+(\\s*=\\s*)[^=;]*((\\s*;\\s*)[\\w-]+(\\s*=\\s*)[^=;]*)*");

    private static final String COOKIE_PAIR_SPLIT_REGEX = "\\s*;\\s*";
    private static final String KEY_VALUE_SPLIT_REGEX = "\\s*=\\s*";

    private static final String ILLEGAL_REQUEST_COOKIE_VALUE_EXCEPTION_MESSAGE_FORMAT =
            "파싱할 수 없는 쿠키값 형식으로 요청을 받았습니다.: %s";

    private final Collection<Cookie> cookies;

    public Cookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }

    public Cookies(Set<Cookie> cookies) {
        this.cookies = cookies;
    }

    public static Cookies from(String rawData) {
        validateValueFormat(rawData);
        Map<String, String> cookies = splitAndThenCollect(rawData, COOKIE_PAIR_SPLIT_REGEX, KEY_VALUE_SPLIT_REGEX);

        return cookies.keySet().stream()
                .map(name -> new Cookie(name, cookies.get(name)))
                .collect(collectingAndThen(toSet(), Cookies::new));
    }

    private static void validateValueFormat(String value) {
        Matcher cookieValueFormatMatcher = HTTP_COOKIE_VALUE_PATTERN.matcher(value);
        if (!cookieValueFormatMatcher.matches()) {
            throw new IllegalRequestDataFormatException(
                    format(ILLEGAL_REQUEST_COOKIE_VALUE_EXCEPTION_MESSAGE_FORMAT, value));
        }
    }

    public boolean hasCookieWithPair(String name, String value) {
        return cookies.stream()
                .anyMatch(cookie -> cookie.hasValue(name, value));
    }

    public boolean containsCookie(String name) {
        return cookies.stream()
                .anyMatch(cookie -> cookie.hasName(name));
    }

    public String get(String name) {
        return cookies.stream()
                .filter(cookie -> cookie.hasName(name))
                .findAny()
                .map(Cookie::getValue)
                .orElse(null);
    }
}
