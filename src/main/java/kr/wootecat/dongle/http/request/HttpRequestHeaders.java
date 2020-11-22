package kr.wootecat.dongle.http.request;

import static com.google.common.net.HttpHeaders.*;
import static java.lang.String.*;
import static kr.wootecat.dongle.http.CookieParser.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.wootecat.dongle.http.Cookie;
import kr.wootecat.dongle.http.Cookies;
import kr.wootecat.dongle.http.exception.IllegalRequestDataFormatException;

public class HttpRequestHeaders {

    private static final Pattern HEADER_PATTERN = Pattern.compile("[\\w-]+: [-,;:+?*/=.()\\s\\w]+");

    private static final String HEADER_KEY_VALUE_DELIMITER = ": ";
    private static final String EMPTY_STRING = "";

    private static final int PAIR_LENGTH = 2;

    private static final String ILLEGAL_REQUEST_FORMAT_EXCEPTION_MESSAGE_FORMAT = "유효하지 않는 요청 데이터 형식힙니다.: %s";
    private static final String LINE_FEED = "\r\n";

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
        Map<String, String> headers = new HashMap<>();
        List<Cookie> cookies = new ArrayList<>();

        String[] headerLines = rawData.split(LINE_FEED);
        for (String headerLine : headerLines) {
            validateHeaderLine(headerLine);
            String[] headerPair = headerLine.split(HEADER_KEY_VALUE_DELIMITER);
            String name = headerPair[0];
            String value = getValueFrom(headerPair);
            if (COOKIE.equals(name)) {
                cookies = toCookie(value);
            } else {
                headers.put(name, value);
            }
        }
        return new HttpRequestHeaders(headers, cookies);
    }

    private static String getValueFrom(String[] pair) {
        return pair.length == PAIR_LENGTH ? pair[1] : EMPTY_STRING;
    }

    private static void validateHeaderLine(String header) {
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
        return cookies.get(name);
    }

    public String get(String key) {
        return headers.get(key);
    }
}
