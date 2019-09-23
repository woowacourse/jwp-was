package webserver.domain;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Response {
    private static final byte[] NEW_LINE = "\r\n".getBytes();

    private ResponseHeader header;
    private ResponseBody body;

    private Response(final ResponseHeader header, final ResponseBody body) {
        this.header = header;
        this.body = body;
    }

    public static class Builder {
        private static final String CONTENT_TYPE = "Content-Type";
        private static final String LOCATION = "Location";
        private ResponseBody body = new ResponseBody();
        private HttpVersion protocol;
        private HttpStatus httpStatus;
        private Map<String, String> responseFields = new HashMap<>();

        public Builder(final HttpVersion protocol, final HttpStatus httpStatus) {
            this.protocol = protocol;
            this.httpStatus = httpStatus;
            this.responseFields.put(CONTENT_TYPE, MediaType.APPLICATION_BINARY.is());
        }

        public Builder(final HttpVersion protocol) {
            this(protocol, HttpStatus.OK);
        }

        public Builder(final HttpStatus httpStatus) {
            this(HttpVersion.HTTP_1_1, httpStatus);
        }

        public Builder() {
            this(HttpVersion.HTTP_1_1, HttpStatus.OK);
        }

        public Builder protocol(final HttpVersion protocol) {
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
            putField(LOCATION, url);
            return this;
        }

        public Builder contentType(final MediaType contentType) {
            this.responseFields.replace(CONTENT_TYPE, contentType.is());
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

        public Builder body(final StaticFile file) {
            this.responseFields.replace(CONTENT_TYPE, MediaType.of(file.getExtension()).is());
            this.body = new ResponseBody(file.getBody());
            return this;
        }

        public Response build() {
            final ResponseHeader header = new ResponseHeader(this.protocol, this.httpStatus, this.responseFields);
            return new Response(header, this.body);
        }
    }

    public byte[] toBytes() {
        final int bodyLength = this.body.length();
        final byte[] header = this.header.make(bodyLength).getBytes(StandardCharsets.UTF_8);

        return ByteBuffer
                .allocate(header.length + NEW_LINE.length + bodyLength)
                .put(header).put(NEW_LINE).put(this.body.getBody())
                .array();
    }
}
