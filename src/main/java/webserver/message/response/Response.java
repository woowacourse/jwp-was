package webserver.message.response;

import webserver.StaticFile;
import webserver.message.HttpCookie;
import webserver.message.HttpStatus;
import webserver.message.HttpVersion;
import webserver.message.MediaType;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response {
    private static final byte[] HEADER_BODY_DELIMITER_BYTES = "\r\n\r\n".getBytes();

    private final ResponseStatusLine statusLine;
    private final ResponseHeader header;
    private final ResponseBody body;

    private Response(final ResponseStatusLine statusLine, final ResponseHeader header, final ResponseBody body) {
        this.statusLine = statusLine;
        this.header = header;
        this.body = body;
    }

    public static class Builder {
        private static final String CONTENT_TYPE = "Content-Type";
        private static final String LOCATION = "Location";

        private HttpVersion httpVersion;
        private HttpStatus httpStatus;
        private ResponseBody body = new ResponseBody();
        private Map<String, String> responseFields = new HashMap<>();
        private List<HttpCookie> cookies = new ArrayList<>();

        public Builder(final HttpVersion httpVersion, final HttpStatus httpStatus) {
            this.httpVersion = httpVersion;
            this.httpStatus = httpStatus;
            this.responseFields.put(CONTENT_TYPE, MediaType.TEXT_HTML.getMediaType());
        }

        public Builder(final String httpVersion) {
            this(HttpVersion.of(httpVersion), HttpStatus.OK);
        }

        public Builder(final HttpVersion httpVersion) {
            this(httpVersion, HttpStatus.OK);
        }

        public Builder(final HttpStatus httpStatus) {
            this(HttpVersion.HTTP_1_1, httpStatus);
        }

        public Builder() {
            this(HttpVersion.HTTP_1_1, HttpStatus.OK);
        }

        public Builder httpVersion(final String httpVersion) {
            this.httpVersion = HttpVersion.of(httpVersion);
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
            this.responseFields.replace(CONTENT_TYPE, contentType.getMediaType());
            return this;
        }

        public Builder addCookie(final HttpCookie cookie) {
            this.cookies.add(cookie);
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
            this.responseFields.replace(CONTENT_TYPE, MediaType.of(file.getExtension()).getMediaType());
            this.body = new ResponseBody(file.getBody());
            return this;
        }

        public Response build() {
            final ResponseStatusLine statusLine = new ResponseStatusLine(this.httpVersion, this.httpStatus);
            final ResponseHeader header = new ResponseHeader(this.responseFields, this.cookies);
            return new Response(statusLine, header, this.body);
        }
    }

    public byte[] toBytes() {
        final int bodyLength = this.body.length();
        final byte[] line = this.statusLine.toBytes();
        final byte[] header = this.header.toBytes(bodyLength);

        return ByteBuffer
                .allocate(line.length + header.length + HEADER_BODY_DELIMITER_BYTES.length + bodyLength)
                .put(line)
                .put(header)
                .put(HEADER_BODY_DELIMITER_BYTES)
                .put(this.body.getBody())
                .array();
    }
}
