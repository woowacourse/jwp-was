package http.response;

import http.common.HttpStatus;

public class ResponseLine {
    private String httpVersion;
    private HttpStatus httpStatus;

    public ResponseLine(String httpVersion, HttpStatus httpStatus) {
        this.httpVersion = httpVersion;
        this.httpStatus = httpStatus;
    }

    public static ResponseLine of() {
        return new ResponseLine("HTTP/1.1", HttpStatus.OK);
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String toString() {
        return httpVersion + " " + httpStatus;
    }
}
