package http.common;

import com.google.common.collect.Lists;
import http.Cookie;
import http.common.exception.InvalidHeaderKeyException;
import http.common.exception.InvalidHttpHeaderException;
import utils.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class HttpHeader {
    public static final String EMPTY_HEADER_NAME_ERROR_MESSAGE = "헤더 이름의 값이 필요합니다.";

    private static final String HEADER_LINE_DELIMITER = ": ";
    public static final String HEADER_VALUES_DELIMITER = "; ";
    private static final String COOKIE = "Cookie";
    private static final String HEADER_FIELD_FORMAT = "%s: %s\r\n";
    private static final int HTTP_HEADER_PARAMETER_SIZE = 2;
    private static final int HEADER_FIELD_KEY_INDEX = 0;
    private static final int HEADER_FIELD_VALUE_INDEX = 1;

    private final Map<String, List<String>> httpHeader = new HashMap<>();
    private final Cookie cookie = new Cookie();

    public HttpHeader() {
    }

    public HttpHeader(List<String> header) {
        if (header != null) {
            header.forEach(this::addHeader);
            cookie.addAll(httpHeader.get(COOKIE));
        }
    }

    private void addHeader(String line) {
        String[] tokens = StringUtils.split(line, HEADER_LINE_DELIMITER);
        checkHeaderParameter(tokens);

        httpHeader.put(tokens[HEADER_FIELD_KEY_INDEX], parseHeaderField(tokens[HEADER_FIELD_VALUE_INDEX]));
    }

    private List<String> parseHeaderField(String headerField) {
        String[] tokens = StringUtils.split(headerField, HEADER_VALUES_DELIMITER);

        return Arrays.stream(tokens)
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.toList());
    }

    private void checkHeaderParameter(String[] tokens) {
        if (tokens == null || tokens.length != HTTP_HEADER_PARAMETER_SIZE) {
            throw new InvalidHttpHeaderException();
        }
    }

    public void addHeaderAttribute(String key, String value) {
        if (httpHeader.containsKey(key)) {
            httpHeader.get(key).add(value);
            return;
        }

        httpHeader.put(key, Lists.newArrayList(value));
    }

    public String getHeaderAttribute(String key) {
        if (StringUtils.isEmpty(key)) {
            throw new InvalidHeaderKeyException(EMPTY_HEADER_NAME_ERROR_MESSAGE);
        }

        return String.join(HEADER_VALUES_DELIMITER, httpHeader.get(key));
    }

    public String serialize() {
        return httpHeader.entrySet().stream()
                .map(entry -> String.format(HEADER_FIELD_FORMAT, entry.getKey(), entry.getValue()))
                .collect(Collectors.joining());
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
