package kr.wootecat.dongle.view;

import static com.google.common.net.HttpHeaders.*;
import static java.lang.String.*;
import static kr.wootecat.dongle.model.http.Cookie.*;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import kr.wootecat.dongle.model.http.Cookie;
import kr.wootecat.dongle.model.http.HttpStatus;
import kr.wootecat.dongle.model.http.response.HttpResponseHeaders;
import kr.wootecat.dongle.model.http.response.HttpStatusLine;

public class HttpResponseDataParser {

    private static final String COOKIE_PAIR_APPEND_DELIMITER = "; ";
    private static final String KEY_VALUE_APPEND_DELIMITER = "=";
    private static final String HTTP_RESPONSE_STATUS_LINE_FORMAT = "%s %d %s\r\n";
    private static final String LINE_FEED = "\r\n";
    private static final String WHITE_SPACE = " ";
    private static final String HEADER_SEPARATOR = ":";

    private HttpResponseDataParser() {
    }

    public static String parseResponseLine(HttpStatusLine httpStatusLine) {
        HttpStatus httpStatus = httpStatusLine.getHttpStatus();
        return format(HTTP_RESPONSE_STATUS_LINE_FORMAT,
                httpStatusLine.getVersion(), httpStatus.getCode(), httpStatus.getReasonPhrase());
    }

    public static String parseResponseHeaders(HttpResponseHeaders responseHeaders) {
        StringBuilder stringBuilder = new StringBuilder();
        appendHeader(responseHeaders, stringBuilder);
        appendCookie(responseHeaders, stringBuilder);
        stringBuilder.append(LINE_FEED);
        return stringBuilder.toString();
    }

    private static void appendHeader(HttpResponseHeaders responseHeaders, StringBuilder stringBuilder) {
        Map<String, String> headers = responseHeaders.getResponseHeaders();
        Set<String> keys = headers.keySet();
        for (String key : keys) {
            appendSingleHeader(stringBuilder, key, headers.get(key));
        }
    }

    private static void appendCookie(HttpResponseHeaders responseHeaders, StringBuilder stringBuilder) {
        Collection<Cookie> cookies = responseHeaders.getCookies();
        for (Cookie cookie : cookies) {
            String result = parseResponseCookieValue(cookie);
            appendSingleHeader(stringBuilder, SET_COOKIE, result);
        }
    }

    private static String parseResponseCookieValue(Cookie cookie) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(cookie.getName());
        stringBuilder.append(KEY_VALUE_APPEND_DELIMITER);
        stringBuilder.append(cookie.getValue());
        if (cookie.hasPath()) {
            stringBuilder.append(COOKIE_PAIR_APPEND_DELIMITER);
            stringBuilder.append(PATH_ATTRIBUTE_NAME);
            stringBuilder.append(KEY_VALUE_APPEND_DELIMITER);
            stringBuilder.append(cookie.getPath());
        }
        return stringBuilder.toString();
    }

    private static void appendSingleHeader(StringBuilder stringBuilder, String key, String value) {
        stringBuilder.append(key);
        stringBuilder.append(HEADER_SEPARATOR);
        stringBuilder.append(WHITE_SPACE);
        stringBuilder.append(value);
        stringBuilder.append(LINE_FEED);
    }
}
