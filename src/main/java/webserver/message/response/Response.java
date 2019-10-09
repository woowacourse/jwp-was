package webserver.message.response;

import webserver.StaticFile;
import webserver.message.HttpCookie;
import webserver.message.HttpStatus;
import webserver.message.HttpVersion;
import webserver.message.MediaType;

import java.nio.ByteBuffer;

public class Response {
    private static final byte[] HEADER_BODY_DELIMITER_BYTES = "\r\n\r\n".getBytes();
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String LOCATION = "Location";

    private final ResponseStatusLine statusLine;
    private final ResponseHeader header;
    private ResponseBody body;

    public Response(final HttpVersion httpVersion) {
        this.statusLine = new ResponseStatusLine(httpVersion, HttpStatus.OK);
        this.header = new ResponseHeader();
        this.body = new ResponseBody();
        addResponseField(CONTENT_TYPE, MediaType.TEXT_HTML.getMediaType());
    }

    public void redirect(final String redirectUrl) {
        setHttpStatus(HttpStatus.FOUND);
        addResponseField(LOCATION, redirectUrl);
    }

    public void setHttpVersion(final HttpVersion httpVersion) {
        this.statusLine.setHttpVersion(httpVersion);
    }

    public void setHttpStatus(final HttpStatus httpStatus) {
        this.statusLine.setHttpStatus(httpStatus);
    }

    public void addResponseField(final String fieldName, String fieldValue) {
        this.header.addResponseField(fieldName, fieldValue);
    }

    public void addCookie(HttpCookie cookie) {
        this.header.addCookie(cookie);
    }

    public void setContentType(final MediaType contentType) {
        this.addResponseField(CONTENT_TYPE, contentType.getMediaType());
    }

    public void body(final String body) {
        this.body = new ResponseBody(body);
    }

    public void body(final byte[] body) {
        this.body = new ResponseBody(body);
    }

    public void body(final StaticFile file) {
        this.addResponseField(CONTENT_TYPE, MediaType.of(file.getExtension()).getMediaType());
        this.body = new ResponseBody(file.getBody());
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
