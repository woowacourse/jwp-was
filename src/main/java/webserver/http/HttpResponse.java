package webserver.http;

import webserver.http.headerfields.HttpConnection;
import webserver.http.headerfields.HttpContentType;
import webserver.http.headerfields.HttpStatusCode;
import webserver.http.headerfields.HttpVersion;

public class HttpResponse {
    private static final String TEXT_PLAIN = "text/plain";

    public static final HttpResponse BAD_REQUEST =
            HttpResponse.builder(HttpContentType.getHttpContentType(TEXT_PLAIN))
                        .statusCode(HttpStatusCode.BAD_REQUEST)
                        .build();

    public static final HttpResponse NOT_FOUND =
            HttpResponse.builder(HttpContentType.getHttpContentType(TEXT_PLAIN))
                        .statusCode(HttpStatusCode.NOT_FOUND)
                        .build();

    public static final HttpResponse INTERNAL_SERVER_ERROR =
            HttpResponse.builder(HttpContentType.getHttpContentType(TEXT_PLAIN))
                        .statusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
                        .build();

    private final HttpVersion version;
    private final HttpStatusCode statusCode;
    private final HttpContentType contentType;
    private final HttpConnection connection;
    private String location;
    private final String body;

    public static class HttpResponseBuilder {
        private HttpVersion version = HttpVersion.HTTP_1_1;
        private HttpStatusCode statusCode = HttpStatusCode.OK;
        private final HttpContentType contentType;
        private HttpConnection connection;
        private String location;
        private String body = "";

        public HttpResponseBuilder(HttpContentType contentType) {
            this.contentType = contentType;
        }

        public HttpResponseBuilder version(HttpVersion httpVersion) {
            this.version = httpVersion;
            return this;
        }

        public HttpResponseBuilder statusCode(HttpStatusCode statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public HttpResponseBuilder connection(HttpConnection httpConnection) {
            this.connection = httpConnection;
            return this;
        }

        public HttpResponseBuilder location(String location) {
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
        this.statusCode = builder.statusCode;
        this.contentType = builder.contentType;
        this.connection = builder.connection;
        this.location = builder.location;
        this.body = builder.body;
    }

    public static HttpResponse success(HttpRequest request, String contentType, String body) {
        return HttpResponse.builder(HttpContentType.getHttpContentType(contentType))
                .version(request.version())
                .connection(request.connection())
                .body(body)
                .build();
    }

    public static HttpResponse redirection(HttpRequest request, String contentType, String location) {
        return HttpResponse.builder(HttpContentType.getHttpContentType(contentType))
                .version(request.version())
                .statusCode(HttpStatusCode.FOUND)
                .connection(request.connection())
                .location(location)
                .build();
    }

    public String serializeHeader() {
        final StringBuilder header = new StringBuilder(serializeMandatory());
        if (this.connection != null) {
            header.append("Connection: " + this.connection + "\r\n");
        }
        if (this.location != null) {
            header.append("Location: " + this.location + "\r\n");
        }
        return header.toString();
    }

    private String serializeMandatory() {
        return String.format(
                "%s %d %s\r\n" +
                "Content-Type: %s\r\n" +
                "Content-Length: %d\r\n",
                this.version,
                this.statusCode.number(),
                this.statusCode.text(),
                this.contentType,
                this.body.length()
        );
    }

    public String serialize() {
        return serializeHeader() + "\r\n" + this.body;
    }

    @Override
    public String toString() {
        return serialize();
    }
}