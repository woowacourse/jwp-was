package webserver.http;

public class HttpResponse {
    public static final HttpResponse BAD_REQUEST =
            HttpResponse.builder(HttpContentType.TEXT_PLAIN_UTF_8)
                        .statusCode(HttpStatusCode.BAD_REQUEST)
                        .build();

    public static final HttpResponse NOT_FOUND =
            HttpResponse.builder(HttpContentType.TEXT_PLAIN_UTF_8)
                        .statusCode(HttpStatusCode.NOT_FOUND)
                        .build();

    public static final HttpResponse INTERNAL_SERVER_ERROR =
            HttpResponse.builder(HttpContentType.TEXT_PLAIN_UTF_8)
                        .statusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
                        .build();

    private final HttpVersion version;
    private final HttpStatusCode statusCode;
    private final HttpContentType contentType;
    private final HttpConnection connection;
    private final String location;
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

        public HttpResponseBuilder extractFromRequest(HttpRequest req) {
            this.version = req.version();
            req.connection().ifPresent(connection -> this.connection = connection);
            return this;
        }

        public HttpResponseBuilder version(HttpVersion version) {
            this.version = version;
            return this;
        }

        public HttpResponseBuilder statusCode(HttpStatusCode statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public HttpResponseBuilder connection(HttpConnection connection) {
            this.connection = connection;
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

    public String serializeHeader() {
        final StringBuilder header = new StringBuilder(serializeMandatory());
        if (this.connection != null) {
            header.append(this.connection.fieldName() + ": " + this.connection + "\r\n");
        }
        if (this.location != null) {
            header.append("Location: " + this.location + "\r\n");
        }
        return header.toString();
    }

    private String serializeMandatory() {
        return String.format(
                "%s %s\r\n" +
                "Content-Type: %s\r\n" +
                "Content-Length: %d\r\n",
                this.version,
                this.statusCode,
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