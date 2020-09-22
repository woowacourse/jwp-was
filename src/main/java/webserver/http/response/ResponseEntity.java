package webserver.http.response;

import java.io.DataOutputStream;
import java.io.IOException;

import webserver.http.HttpHeaders;

public class ResponseEntity<T> {
    private final HttpStatus httpStatus;
    private final HttpHeaders httpHeaders;
    private final T body;

    public ResponseEntity(HttpStatus httpStatus, HttpHeaders httpHeaders, T body) {
        this.httpStatus = httpStatus;
        this.httpHeaders = httpHeaders;
        this.body = body;
    }

    public ResponseEntity(HttpStatus httpStatus, T body) {
        this(httpStatus, null, body);
    }

    public ResponseEntity(HttpStatus httpStatus, HttpHeaders httpHeaders) {
        this(httpStatus, httpHeaders, null);
    }

    public static <T> ResponseEntity<T> ok(T body) {
        return new ResponseEntity<>(HttpStatus.OK, body);
    }

    public static ResponseEntity<Void> found(HttpHeaders httpHeaders) {
        return new ResponseEntity<>(HttpStatus.FOUND, httpHeaders);
    }

    public void writeHeaders(DataOutputStream dataOutputStream) throws IOException {
        httpHeaders.write(dataOutputStream);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public T getBody() {
        return body;
    }

    public boolean isBodyNull() {
        return null == body;
    }
}
