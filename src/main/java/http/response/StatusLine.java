package http.response;

import http.common.HttpVersion;

public class StatusLine {

    private HttpVersion httpVersion;
    private HttpStatus httpStatus;

    public StatusLine(final HttpVersion httpVersion, final HttpStatus httpStatus) {
        this.httpVersion = httpVersion;
        this.httpStatus = httpStatus;
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getCodeAndStatus() {
        return httpStatus.getCodeAndStatus();
    }
}
