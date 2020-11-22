package kr.wootecat.dongle.model.http.request;

import static com.google.common.net.HttpHeaders.*;
import static java.lang.String.*;
import static utils.SplitUtils.*;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.wootecat.dongle.model.http.Cookie;
import kr.wootecat.dongle.model.http.Cookies;
import kr.wootecat.dongle.model.http.exception.IllegalRequestDataFormatException;

public class HttpRequestHeaders {

    private static final Pattern HEADER_PATTERN = Pattern.compile("([\\w-]+: [-,;:+?*/=.()\\s\\w]+\r\n)+");

    private static final String HEADER_KEY_VALUE_DELIMITER = ":\\s+";
    private static final String LINE_FEED = "\r\n";

    private static final String ILLEGAL_REQUEST_FORMAT_EXCEPTION_MESSAGE_FORMAT = "유효하지 않는 요청 데이터 형식힙니다.: %s";
    private static final String EMPTY_VALUE = "";

    private final Map<String, String> headers;
    private final Cookies cookies;

    public HttpRequestHeaders(Map<String, String> headers, List<Cookie> cookies) {
        this(headers, new Cookies(cookies));
    }

    public HttpRequestHeaders(Map<String, String> headers, Cookies cookies) {
        this.headers = headers;
        this.cookies = cookies;
    }

    public static HttpRequestHeaders from(String rawData) {
        validateHttpHeaders(rawData);
        Map<String, String> headerPairs = splitAndThenCollect(rawData, LINE_FEED, HEADER_KEY_VALUE_DELIMITER);
        String cookieValue = headerPairs.getOrDefault(COOKIE, EMPTY_VALUE);
        headerPairs.computeIfPresent(COOKIE, (key, value) -> null);
        Cookies cookies = Cookies.from(cookieValue);

        return new HttpRequestHeaders(headerPairs, cookies);
    }

    private static void validateHttpHeaders(String header) {
        Matcher headerMatcher = HEADER_PATTERN.matcher(header);
        if (!headerMatcher.matches()) {
            throw new IllegalRequestDataFormatException(
                    format(ILLEGAL_REQUEST_FORMAT_EXCEPTION_MESSAGE_FORMAT, header));
        }
    }

    public boolean containsHeader(String name) {
        return headers.containsKey(name);
    }

    public boolean hasCookieWithPair(String name, boolean value) {
        return hasCookieWithPair(name, String.valueOf(value));
    }

    public boolean hasCookieWithPair(String name, String value) {
        return cookies.hasCookieWithPair(name, value);
    }

    public boolean containsCookie(String name) {
        return cookies.containsCookie(name);
    }

    public String getCookie(String name) {
        return cookies.getValue(name);
    }

    public String get(String key) {
        return headers.get(key);
    }
}
