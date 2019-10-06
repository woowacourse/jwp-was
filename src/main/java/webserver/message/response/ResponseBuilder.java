package webserver.message.response;

import webserver.StaticFile;
import webserver.message.HttpCookie;
import webserver.message.HttpStatus;
import webserver.message.HttpVersion;
import webserver.message.MediaType;

import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String LOCATION = "Location";

    private HttpVersion httpVersion;
    private HttpStatus httpStatus;
    private ResponseBody body = new ResponseBody();
    private Map<String, String> responseFields = new HashMap<>();
    private Map<String, HttpCookie> cookies = new HashMap<>();

    public ResponseBuilder(final HttpVersion httpVersion, final HttpStatus httpStatus) {
        this.httpVersion = httpVersion;
        this.httpStatus = httpStatus;
        this.responseFields.put(CONTENT_TYPE, MediaType.TEXT_HTML.getMediaType());
    }

    public ResponseBuilder(final String httpVersion) {
        this(HttpVersion.of(httpVersion), HttpStatus.OK);
    }

    public ResponseBuilder(final HttpVersion httpVersion) {
        this(httpVersion, HttpStatus.OK);
    }

    public ResponseBuilder(final HttpStatus httpStatus) {
        this(HttpVersion.HTTP_1_1, httpStatus);
    }

    public ResponseBuilder() {
        this(HttpVersion.HTTP_1_1, HttpStatus.OK);
    }

    public ResponseBuilder httpVersion(final String httpVersion) {
        this.httpVersion = HttpVersion.of(httpVersion);
        return this;
    }

    public ResponseBuilder httpStatus(final HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    public ResponseBuilder putField(final String fieldName, String fieldValue) {
        this.responseFields.put(fieldName, fieldValue);
        return this;
    }

    public ResponseBuilder redirectUrl(final String url) {
        this.httpStatus = HttpStatus.FOUND;
        putField(LOCATION, url);
        return this;
    }

    public ResponseBuilder contentType(final MediaType contentType) {
        this.responseFields.replace(CONTENT_TYPE, contentType.getMediaType());
        return this;
    }

    public ResponseBuilder addCookie(final HttpCookie cookie) {
        this.cookies.put(cookie.getName(), cookie);
        return this;
    }

    public ResponseBuilder body(final String body) {
        this.body = new ResponseBody(body);
        return this;
    }

    public ResponseBuilder body(final byte[] body) {
        this.body = new ResponseBody(body);
        return this;
    }

    public ResponseBuilder body(final StaticFile file) {
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