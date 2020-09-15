package http.response;

import java.util.Map;

import http.HttpBody;
import http.HttpHeader;
import http.request.ContentType;

public class ResponseEntity {

    private String httpVersion;
    private HttpStatus httpStatus;
    private HttpHeader httpHeader;
    private HttpBody httpBody;

    public ResponseEntity(HttpStatus httpStatus) {
        this(null, httpStatus, HttpHeader.empty(), null);
    }

    public ResponseEntity(String httpVersion, HttpStatus httpStatus, HttpHeader httpHeader, HttpBody httpBody) {
        this.httpVersion = httpVersion;
        this.httpStatus = httpStatus;
        this.httpHeader = httpHeader;
        this.httpBody = httpBody;
    }

    public static ResponseEntity status(HttpStatus httpStatus) {
        return new ResponseEntity(httpStatus);
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
        this.httpBody = new HttpBody(body, contentType);
        return this;
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
