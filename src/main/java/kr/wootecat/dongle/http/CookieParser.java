package kr.wootecat.dongle.http;

import static java.lang.String.*;
import static java.util.Collections.*;
import static kr.wootecat.dongle.http.Cookie.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.wootecat.dongle.http.exception.IllegalCookieFormatException;

public class CookieParser {

    private static final Pattern HTTP_COOKIE_VALUE_PATTERN = Pattern.compile(
            "[\\w-]+(\\s*=\\s*)[\\w-]*((\\s*;\\s*)[\\w-]+(\\s*=\\s*)[\\w-]*)*");

    private static final String COOKIE_PAIR_SPLIT_REGEX = "\\s*;\\s*";
    private static final String KEY_VALUE_SPLIT_REGEX = "\\s*=\\s*";

    private static final String COOKIE_PAIR_APPEND_DELIMITER = "; ";
    private static final String KEY_VALUE_APPEND_DELIMITER = "=";
    private static final String EMPTY_COOKIE_VALUE = "";

    private static final int KEY_VALUE_PAIR_LENGTH = 2;

    private static final String ILLEGAL_REQUEST_COOKIE_VALUE_EXCEPTION_MESSAGE_FORMAT =
            "파싱할 수 없는 쿠키값 형식으로 요청을 받았습니다.: %s";

    private CookieParser() {
    }

    public static String toHttpResponseFormat(Cookie cookie) {
        String name = cookie.getName();
        String value = cookie.getValue();

        String result = name + KEY_VALUE_APPEND_DELIMITER + value;
        if (cookie.hasPath()) {
            result += COOKIE_PAIR_APPEND_DELIMITER;
            result += PATH_ATTRIBUTE_NAME + KEY_VALUE_APPEND_DELIMITER + cookie.getPath();
        }

        return result;
    }

    public static List<Cookie> toCookie(String value) {
        validateValueFormat(value);

        List<Cookie> cookies = new ArrayList<>();
        String[] keyValue = value.split(COOKIE_PAIR_SPLIT_REGEX);
        for (String cookiePair : keyValue) {
            String[] split = cookiePair.split(KEY_VALUE_SPLIT_REGEX);
            String cookieName = split[0];
            String cookieValue = split.length == KEY_VALUE_PAIR_LENGTH ? split[1] : EMPTY_COOKIE_VALUE;
            cookies.add(new Cookie(cookieName, cookieValue));
        }
        return unmodifiableList(cookies);
    }

    private static void validateValueFormat(String value) {
        Matcher cookieValueFormatMatcher = HTTP_COOKIE_VALUE_PATTERN.matcher(value);
        if (!cookieValueFormatMatcher.matches()) {
            throw new IllegalCookieFormatException(
                    format(ILLEGAL_REQUEST_COOKIE_VALUE_EXCEPTION_MESSAGE_FORMAT, value));
        }
    }
}
