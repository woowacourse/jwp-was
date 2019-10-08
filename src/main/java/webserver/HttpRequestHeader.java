package webserver;

import webserver.httpelement.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public final class HttpRequestHeader {
    private final HttpHost host;
    private final HttpContentType contentType;
    private final HttpCookie cookie;
    private final HttpConnection connection;
    private final Map<String, String> otherFields;

    public static Optional<HttpRequestHeader> of(Map<String, String> fields) {
        return retrieveValue(HttpHost.class, fields).flatMap(val ->
        HttpHost.of(val).map(host ->
            new HttpRequestHeader(host, fields)
        ));
    }

    private static Optional<String> retrieveValue(Class fieldType, Map<String, String> headerFields) {
        return Optional.ofNullable(headerFields.remove(HttpHeaderField.getName(fieldType)));
    }

    private HttpRequestHeader(HttpHost host, Map<String, String> fields) {
        this.host = host;
        this.contentType = retrieveValue(HttpContentType.class, fields).flatMap(HttpContentType::of).orElse(null);
        this.cookie = retrieveValue(HttpCookie.class, fields).flatMap(HttpCookie::of).orElse(null);
        this.connection = retrieveValue(HttpConnection.class, fields).flatMap(HttpConnection::of).orElse(null);
        this.otherFields = Collections.unmodifiableMap(fields);
    }

    public Optional<HttpContentType> contentType() {
        return Optional.ofNullable(this.contentType);
    }

    public String getCookieOf(String key) {
        return Optional.ofNullable(this.cookie)
                        .map(x -> x.get(key))
                        .orElse(null);
    }

    public Optional<HttpConnection> connection() {
        return Optional.ofNullable(this.connection);
    }

    @Override
    public String toString() {
        return (new StringBuilder()).append(fieldToString(this.host))
                                    .append(fieldToString(this.contentType))
                                    .append(fieldToString(this.cookie))
                                    .append(fieldToString(this.connection))
                                    .append(otherFieldsToString())
                                    .toString();
    }

    private String fieldToString(HttpHeaderField headerField) {
        return Optional.ofNullable(headerField).map(x ->
                String.format("%s: %s\r\n", x.fieldName(), x)
        ).orElse("");
    }

    private String otherFieldsToString() {
        final StringBuilder acc = new StringBuilder();
        this.otherFields.forEach((key, value) -> acc.append(String.format("%s: %s\r\n", key, value)));
        return acc.toString();
    }
}