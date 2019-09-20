package webserver.http;

public class HttpResponse {
    private HttpStatusCode statusCode;
    private HttpContentType httpContentType;
    private byte[] body;

    public static class builder {
        private HttpStatusCode statusCode = HttpStatusCode.OK;
        private HttpContentType httpContentType;
        private byte[] body = {};

        public builder() {}

        public builder httpStatusCode(HttpStatusCode statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public builder httpContentType(HttpContentType httpContentType) {
            this.httpContentType = httpContentType;
            return this;
        }

        public builder body(byte[] body) {
            this.body = body;
            return this;
        }

        public HttpResponse build() {
            return new HttpResponse(this);
        }
    }

    private HttpResponse(builder builder) {
        this.statusCode = builder.statusCode;
        this.httpContentType = builder.httpContentType;
        this.body = builder.body;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    public HttpContentType getHttpContentType() {
        return httpContentType;
    }

    public byte[] getBody() {
        return body;
    }
}