package http.common;

import com.google.common.collect.Lists;
import http.common.exception.InvalidHeaderKeyException;
import http.common.exception.InvalidHttpHeaderException;
import http.cookie.Cookie;
import http.cookie.CookieParser;
import http.cookie.Cookies;
import utils.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.net.HttpHeaders.COOKIE;

public class HttpHeader {
    public static final String HEADER_FIELD_FORMAT = "%s: %s\r\n";
    private static final String EMPTY_HEADER_NAME_ERROR_MESSAGE = "헤더 이름의 값이 필요합니다.";
    private static final String HEADER_LINE_DELIMITER = ": ";
    private static final String HEADER_VALUES_DELIMITER = ";";
    private static final int LINE_DELIMITER_LENGTH = HEADER_LINE_DELIMITER.length();
    private static final int ZERO = 0;
    private static final int INVALID_SEPARATOR_POSITION = -1;

    private final Map<String, List<String>> httpHeader = new HashMap<>();
    private final Cookies cookies = new Cookies();

    public HttpHeader() {
    }

    public HttpHeader(List<String> header) {
        if (header != null) {
            header.forEach(this::addHeader);
            cookies.addAll(CookieParser.parse(httpHeader.get(COOKIE)));
        }
    }

    private void addHeader(String line) {
        int separatorPosition = getSeparatorPosition(line);
        if (separatorPosition <= ZERO) {
            throw new InvalidHttpHeaderException();
        }
        httpHeader.put(line.substring(ZERO, separatorPosition),
                parseHeaderField(line.substring(separatorPosition + LINE_DELIMITER_LENGTH)));
    }

    private int getSeparatorPosition(String line) {
        return StringUtils.isEmpty(line) ? INVALID_SEPARATOR_POSITION : line.indexOf(HEADER_LINE_DELIMITER);
    }

    private List<String> parseHeaderField(String headerField) {
        String[] tokens = StringUtils.split(headerField, HEADER_VALUES_DELIMITER);

        return Arrays.stream(tokens)
                .map(String::trim)
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.toList());
    }

    public void addHeaderAttribute(String key, String value) {
        if (httpHeader.containsKey(key)) {
            httpHeader.get(key).add(value);
            return;
        }

        httpHeader.put(key, Lists.newArrayList(value));
    }

    //TODO: return type을 List로 변환하기
    public String getHeaderAttribute(String key) {
        if (StringUtils.isEmpty(key)) {
            throw new InvalidHeaderKeyException(EMPTY_HEADER_NAME_ERROR_MESSAGE);
        }

        List<String> values = httpHeader.get(key);
        return values != null ? String.join(HEADER_VALUES_DELIMITER, values) : null;
    }

    public void addCookie(Cookie cookie) {
        if (cookie != null) {
            cookies.add(cookie);
        }
    }

    public String getCookieAttribute(String name) {
        return cookies.getCookie(name).getValue();
    }

    public String serialize() {
        StringBuilder stringBuilder = new StringBuilder();
        httpHeader.entrySet().stream()
                .map(this::getFormattedHeader)
                .forEach(stringBuilder::append);
        stringBuilder.append(cookies.serialize());
        return stringBuilder.toString();
    }

    private String getFormattedHeader(Map.Entry<String, List<String>> entry) {
        return String.format(HEADER_FIELD_FORMAT,
                entry.getKey(), String.join(HEADER_VALUES_DELIMITER, entry.getValue()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpHeader that = (HttpHeader) o;
        return Objects.equals(httpHeader, that.httpHeader);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpHeader);
    }
}
