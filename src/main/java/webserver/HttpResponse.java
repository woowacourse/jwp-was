package webserver;

import webserver.httpelement.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public final class HttpResponse {
    public static final HttpResponse BAD_REQUEST = HttpResponse.builder(HttpContentType.TEXT_PLAIN_UTF_8)
                                                                .status(HttpStatus.BAD_REQUEST)
                                                                .build();
    public static final HttpResponse NOT_FOUND = HttpResponse.builder(HttpContentType.TEXT_PLAIN_UTF_8)
                                                            .status(HttpStatus.NOT_FOUND)
                                                            .build();
    public static final HttpResponse INTERNAL_SERVER_ERROR = HttpResponse.builder(HttpContentType.TEXT_PLAIN_UTF_8)
                                                                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                                                    .build();

    private final HttpVersion version;
    private final HttpStatus status;
    private final HttpContentType contentType;
    private final List<HttpSetCookie> cookies;
    private final HttpConnection connection;
    private final HttpLocation location;
    private final String body;

    public static class HttpResponseBuilder {
        private HttpVersion version = HttpVersion.HTTP_1_1;
        private HttpStatus status = HttpStatus.OK;
        private final HttpContentType contentType;
        private List<HttpSetCookie> cookies = new ArrayList<>();
        private HttpConnection connection;
        private HttpLocation location;
        private String body = "";

        public HttpResponseBuilder(HttpContentType contentType) {
            this.contentType = contentType;
        }

        public HttpResponseBuilder extractFieldsFromRequest(HttpRequest req) {
            this.version = req.version();
            req.connection().ifPresent(connection -> this.connection = connection);
            return this;
        }

        public HttpResponseBuilder version(HttpVersion version) {
            this.version = version;
            return this;
        }

        public HttpResponseBuilder status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public HttpResponseBuilder addCookie(HttpSetCookie cookie) {
            this.cookies.add(cookie);
            return this;
        }

        public HttpResponseBuilder connection(HttpConnection connection) {
            this.connection = connection;
            return this;
        }

        public HttpResponseBuilder location(HttpLocation location) {
            this.location = location;
            return this;
        }

        public HttpResponseBuilder body(String body) {
            this.body = body;
            return this;
        }

        public HttpResponse build() {
            return new HttpResponse(this);
        }
    }

    public static HttpResponseBuilder builder(HttpContentType contentType) {
        return new HttpResponseBuilder(contentType);
    }

    private HttpResponse(HttpResponseBuilder builder) {
        this.version = builder.version;
        this.status = builder.status;
        this.contentType = builder.contentType;
        this.cookies = (builder.cookies.isEmpty()) ? Collections.emptyList() : builder.cookies;
        this.connection = builder.connection;
        this.location = builder.location;
        this.body = builder.body;
    }

    private StringBuilder serializeHeader() {
        return serializeOptionals(
                new StringBuilder(serializeMandatory()),
                this.cookies,
                this.connection,
                this.location
        );
    }

    private String serializeMandatory() {
        return String.format(
                "%s %s\r\nContent-Type: %s\r\nContent-Length: %d\r\n",
                this.version,
                this.status,
                this.contentType,
                this.body.length()
        );
    }

    private StringBuilder serializeOptionals(
            StringBuilder header,
            List<HttpSetCookie> cookies,
            HttpHeaderField... fields
    ) {
        cookies.forEach(attr ->
                header.append(String.format("%s: %s\r\n", HttpHeaderField.getName(HttpSetCookie.class), attr))
        );
        Stream.of(fields).forEach(field -> {
            if (field != null) {
                header.append(String.format("%s: %s\r\n", field.fieldName(), field));
            }
        });
        return header;
    }

    public String serialize() {
        return serializeHeader().append("\r\n").append(this.body).toString();
    }

    public String printHeader() {
        return serializeHeader().toString();
    }
    @Override
    public String toString() {
        return serialize();
    }
}