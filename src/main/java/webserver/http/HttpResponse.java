package webserver.http;

public class HttpResponse {
    private HttpStatusCode statusCode;
    private HttpContentType httpContentType;
    private byte[] body;

    public static class Builder {
        private HttpStatusCode statusCode = HttpStatusCode.OK;
        private HttpContentType httpContentType;
        private byte[] body = {};

        public Builder() {

        }

        public Builder httpStatusCode(HttpStatusCode statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder httpContentType(HttpContentType httpContentType) {
            this.httpContentType = httpContentType;
            return this;
        }

        public Builder body(byte[] body) {
            this.body = body;
            return this;
        }

        public HttpResponse build() {
            return new HttpResponse(this);
        }
    }

    private HttpResponse(Builder builder) {
        this.statusCode = builder.statusCode;
        this.httpContentType = builder.httpContentType;
        this.body = builder.body;
        new HttpResponse.Builder().
    }
}