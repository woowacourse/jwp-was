package webserver.http.request.core;

import webserver.http.HttpHeaderField;
import webserver.http.exception.CanNotParseTokensException;
import webserver.http.exception.HeaderParsingException;
import webserver.http.exception.NoMatchHeaderFieldException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestHeader {
    private static final String DELIMITER = ": ";
    private static final int KEY_TOKEN = 0;
    private static final int VALUE_TOKEN = 1;

    private final Map<HttpHeaderField, String> requestHeaders = new HashMap<>();

    public RequestHeader(List<String> lines) {
        parse(lines);
    }

    private void parse(List<String> requestHeaderLines) {
        requestHeaderLines.stream()
                .filter(HttpHeaderField::hasField)
                .forEach(line -> {
                    String[] tokens = parseLineTokens(line);
                    if (tokens.length != 2) {
                        throw new CanNotParseTokensException();
                    }
                    HttpHeaderField key = HttpHeaderField.of(tokens[KEY_TOKEN]);
                    String value = tokens[VALUE_TOKEN];
                    requestHeaders.put(key, value);
                });
    }

    private String[] parseLineTokens(String line) {
        if (!line.contains(DELIMITER)) {
            throw new HeaderParsingException();
        }
        return line.split(DELIMITER);
    }

    public String getHeadersKey(String field) {
        HttpHeaderField httpHeaderField = requestHeaders.keySet().stream()
                .filter(key -> key.getField().equals(field))
                .findFirst()
                .orElseThrow(NoMatchHeaderFieldException::new);

        return requestHeaders.get(httpHeaderField);
    }

    public boolean hasHeaderField(String cookie) {
        return requestHeaders.keySet().stream()
                .anyMatch(key -> key.getField().equals(cookie));
    }
}
