package http.parser;

import http.common.HttpHeader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestHeaderParser {

    private static final String HEADER_DELIMITER = ":";

    public static HttpHeader parse(List<String> headerLines) {
        Map<String, String> headers = new HashMap<>();

        headerLines.forEach(headerLine -> {
            String[] headerTokens = headerLine.split(HEADER_DELIMITER, 2);
            String name = headerTokens[0].trim();
            String value = headerTokens[1].trim();
            headers.put(name, value);
        });
        return new HttpHeader(headers);
    }
}
