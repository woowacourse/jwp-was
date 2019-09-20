package http.response;

import http.common.HttpStatus;

public class ResponseStartLine {
    private String httpVersion;
    private HttpStatus httpStatus;

    public ResponseStartLine(String httpVersion, HttpStatus httpStatus) {
        this.httpVersion = httpVersion;
        this.httpStatus = httpStatus;
    }

    public static ResponseStartLine of() {
        return new ResponseStartLine("HTTP/1.1", HttpStatus.OK);
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String toString() {
        return httpVersion + " " + httpStatus;
    }
}
