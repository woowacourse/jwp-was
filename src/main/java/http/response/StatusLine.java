package http.response;

import http.common.HttpVersion;

public class StatusLine {

    private final HttpStatus httpStatus;
    private final HttpVersion httpVersion;

    public StatusLine(HttpStatus httpStatus, HttpVersion httpVersion) {
        this.httpStatus = httpStatus;
        this.httpVersion = httpVersion;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    @Override
    public String toString() {
        return "StatusLine{" +
                "httpStatus=" + httpStatus +
                ", httpVersion=" + httpVersion +
                '}';
    }
}
