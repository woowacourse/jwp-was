package http.common;

import http.common.exception.InvalidHeaderKeyException;
import http.common.exception.InvalidHttpHeaderException;
import utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class HttpHeader {
    private static final int HTTP_HEADER_PARAMETER_SIZE = 2;
    private static final String HEADER_FIELD_SPLIT_DELIMITER = ": ";
    private static final String HEADER_FIELD_FORMAT = "%s: %s\r\n";
    private static final int HEADER_FIELD_KEY_INDEX = 0;
    private static final int HEADER_FIELD_VALUE_INDEX = 1;

    private final Map<String, String> httpHeader = new HashMap<>();

    public HttpHeader() {
    }

    public HttpHeader(List<String> header) {
        if (header != null) {
            header.forEach(this::addHeader);
        }
    }

    private void addHeader(String line) {
        String[] tokens = StringUtils.split(line, HEADER_FIELD_SPLIT_DELIMITER);
        checkHeaderParameter(tokens);

        httpHeader.put(tokens[HEADER_FIELD_KEY_INDEX], tokens[HEADER_FIELD_VALUE_INDEX]);
    }

    private void checkHeaderParameter(String[] tokens) {
        if (tokens == null || tokens.length != HTTP_HEADER_PARAMETER_SIZE) {
            throw new InvalidHttpHeaderException();
        }
    }


    public String getHeaderAttribute(String key) {
        if (StringUtils.isEmpty(key)) {
            throw new InvalidHeaderKeyException();
        }

        return httpHeader.get(key);
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

    public void add(String key, String value) {
        this.httpHeader.put(key, value);
    }
}
