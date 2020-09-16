package http.response;

import java.util.Map;

import http.HttpBody;
import http.HttpHeader;
import http.request.ContentType;

public class ResponseEntity {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String SEPERATOR = " ";

    private String httpVersion;
    private HttpStatus httpStatus;
    private HttpHeader httpHeader;
    private HttpBody httpBody;

    private ResponseEntity() {
        this(null, null, HttpHeader.empty(), HttpBody.empty());
    }

    private ResponseEntity(HttpStatus httpStatus) {
        this(null, httpStatus, HttpHeader.empty(), HttpBody.empty());
    }

    private ResponseEntity(String httpVersion, HttpStatus httpStatus, HttpHeader httpHeader, HttpBody httpBody) {
        this.httpVersion = httpVersion;
        this.httpStatus = httpStatus;
        this.httpHeader = httpHeader;
        this.httpBody = httpBody;
    }

    public static ResponseEntity empty() {
        return new ResponseEntity();
    }

    public static ResponseEntity generateWithStatus(HttpStatus httpStatus) {
        return new ResponseEntity(httpStatus);
    }

    public ResponseEntity status(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    public ResponseEntity version(String version) {
        this.httpVersion = version;
        return this;
    }

    public ResponseEntity addHeaders(Map<String, String> headers) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            this.httpHeader.addHeader(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public ResponseEntity addHeader(String key, String value) {
        this.httpHeader.addHeader(key, value);
        return this;
    }

    public ResponseEntity body(String body, ContentType contentType) {
        this.httpBody = HttpBody.of(body, contentType);
        this.httpHeader.setOrReplaceHeader(CONTENT_TYPE, contentType.getType());
        this.httpHeader.setOrReplaceHeader(CONTENT_LENGTH, String.valueOf(body.length()));
        return this;
    }

    public String convertToString() {
        return httpVersion + SEPERATOR + httpStatus.convertToString() + SEPERATOR + System.lineSeparator()
            + httpHeader.convertToString() + System.lineSeparator()
            + httpBody.getContent();
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }

    public HttpBody getHttpBody() {
        return httpBody;
    }
}
