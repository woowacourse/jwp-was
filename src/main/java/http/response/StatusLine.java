package http.response;

import http.common.HttpStatus;

public class StatusLine {
    private String httpVersion;
    private HttpStatus httpStatus;

    public StatusLine(String httpVersion, HttpStatus httpStatus) {
        this.httpVersion = httpVersion;
        this.httpStatus = httpStatus;
    }

    public static StatusLine of() {
        return new StatusLine("HTTP/1.1", HttpStatus.OK);
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String toString() {
        return httpVersion + " " + httpStatus;
    }
}
