package http.common;

import http.common.exception.HttpHeaderNotFoundException;
import http.common.exception.InvalidHttpHeaderException;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class HttpHeader {

    public static final int HTTP_HEADER_PARAMETER_SIZE = 2;
    private final Map<String, String> httpHeader = new HashMap<>();

    public HttpHeader(List<String> header) {
        checkHeader(header);
        header.forEach(this::addHeader);
    }

    private void checkHeader(List<String> header) {
        if (header == null) {
            throw new InvalidHttpHeaderException();
        }
    }

    private void addHeader(String line) {
        String[] tokens = StringUtils.split(line, ": ");
        checkHeaderParameter(tokens);

        httpHeader.put(tokens[0], tokens[1]);
    }

    private void checkHeaderParameter(String[] tokens) {
        if (tokens == null || tokens.length != HTTP_HEADER_PARAMETER_SIZE) {
            throw new InvalidHttpHeaderException();
        }
    }


    public String get(String key) {
        if (StringUtils.isEmpty(key)) {
            throw new HttpHeaderNotFoundException();
        }

        return httpHeader.get(key);
    }

    public String serialize() {
        return httpHeader.entrySet().stream()
                .map(entry -> String.format("%s: %s\r\n", entry.getKey(), entry.getValue()))
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
