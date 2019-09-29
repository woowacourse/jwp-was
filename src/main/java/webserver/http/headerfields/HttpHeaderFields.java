package webserver.http.headerfields;

import utils.parser.KeyValueParser;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class HttpHeaderFields {
    private static final String CONNECTION = "Connection";
    private static final String CONTENT_LENGTH = "Content-Length";

    private Map<String, String> headerFields;

    private HttpHeaderFields(Map<String, String> headerFields) {
        this.headerFields = Collections.unmodifiableMap(headerFields);
    }

    public static HttpHeaderFields init(Map<String, String> headerFields) {
        return new HttpHeaderFields(headerFields);
    }

    public String debugString() {
        return KeyValueParser.debugString(this.headerFields);
    }

    public String value(String key) {
        return headerFields.get(key);
    }

    public Optional<HttpConnection> connection() {
        return HttpConnection.of(headerFields.get(CONNECTION));
    }

    public String contentLength() {
        return headerFields.get(CONTENT_LENGTH);
    }

    public Optional<String> responseString() {
        StringBuilder result = new StringBuilder();

        headerFields.keySet().forEach(key ->
                result.append(key)
                        .append(": ")
                        .append(headerFields.get(key))
                        .append("\r\n")
        );
        return Optional.ofNullable(result.toString());
    }
}