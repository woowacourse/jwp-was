package webserver.domain;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class Response {
    private static final byte[] NEW_LINE_BYTES = "\r\n".getBytes();

    private ResponseHeader header;
    private ResponseBody body;

    private Response(final ResponseHeader header, final ResponseBody body) {
        this.header = header;
        this.body = body;
    }

    public static class Builder {
        private static final String HTTP_1_1 = "HTTP/1.1";

        private ResponseBody body = new ResponseBody();
        private String protocol;
        private HttpStatus httpStatus;
        private Map<String, String> responseFields = new HashMap<>();

        public Builder(final String protocol, final HttpStatus httpStatus) {
            this.protocol = protocol;
            this.httpStatus = httpStatus;
            this.responseFields.put("Content-Type", "text/html; charset=utf-8");
        }

        public Builder(final String protocol) {
            this(protocol, HttpStatus.OK);
        }

        public Builder(final HttpStatus httpStatus) {
            this(HTTP_1_1, httpStatus);
        }

        public Builder() {
            this(HTTP_1_1, HttpStatus.OK);
        }

        public Builder protocol(final String protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder httpStatus(final HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public Builder putField(final String fieldName, String fieldValue) {
            this.responseFields.put(fieldName, fieldValue);
            return this;
        }

        public Builder redirectUrl(final String url) {
            this.httpStatus = HttpStatus.FOUND;
            putField("Location", url);
            return this;
        }

        public Builder contentType(final String contentType) {
            this.responseFields.replace("Content-Type", contentType + "; charset=utf-8");
            return this;
        }

        public Builder body(final String body) {
            this.body = new ResponseBody(body);
            return this;
        }

        public Builder body(final byte[] body) {
            this.body = new ResponseBody(body);
            return this;
        }

        public Response build() {
            final ResponseHeader header = new ResponseHeader(this.protocol, this.httpStatus, this.responseFields);
            return new Response(header, this.body);
        }
    }

    public byte[] toBytes() {
        final int bodyLength = this.body.length();
        final byte[] header = this.header.make(bodyLength).getBytes();

        return ByteBuffer
                .allocate(header.length + NEW_LINE_BYTES.length + bodyLength)
                .put(header).put(NEW_LINE_BYTES).put(this.body.getBody())
                .array();
    }
}
