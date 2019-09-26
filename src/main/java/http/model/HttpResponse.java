package http.model;

import utils.FileIoUtils;

import static com.google.common.net.HttpHeaders.CONTENT_LENGTH;
import static com.google.common.net.HttpHeaders.LOCATION;

public class HttpResponse {
    private StatusLine statusLine;
    private HttpHeaders httpHeaders;
    private byte[] body;

    private HttpResponse(Builder builder) {
        this.statusLine = new StatusLine(builder.httpProtocols, builder.httpStatus);
        this.httpHeaders = builder.httpHeaders;
        this.body = builder.body;
    }

    public StatusLine getStatusLine() {
        return statusLine;
    }

    public String getHeader(String key) {
        return httpHeaders.getHeader(key);
    }

    public byte[] getBody() {
        return body;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public static class Builder {
        private HttpProtocols httpProtocols;
        private HttpStatus httpStatus;
        private HttpHeaders httpHeaders;
        private byte[] body;


        public Builder() {
            httpHeaders = new HttpHeaders();
        }

        public Builder protocols(HttpProtocols httpProtocols) {
            this.httpProtocols = httpProtocols;
            return this;
        }

        public Builder status(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public Builder body(byte[] body) {
            this.body = body;
            return this;
        }

        public Builder addHeader(String key, String value) {
            httpHeaders.addHeader(key, value);
            return this;
        }

        public Builder forward(String filePath) {
            this.body = FileIoUtils.loadFileFromClasspath(filePath);
            httpHeaders.addHeader(CONTENT_LENGTH, Integer.toString(body.length));
            return this;
        }

        public Builder sendRedirect(String filePath) {
            httpHeaders.addHeader(LOCATION, filePath);
            return this;
        }

        public HttpResponse build() {
            return new HttpResponse(this);
        }
    }
}
