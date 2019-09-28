package webserver.http.response;

import webserver.http.HttpVersion;

public class StatusLine {
    public static final HttpVersion DEFAULT_HTTP_VERSION = HttpVersion.HTTP_1_1;

    private HttpVersion httpVersion = DEFAULT_HTTP_VERSION;
    private HttpStatus httpStatus;

    StatusLine() {
    }

    StatusLine(final HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    StatusLine(final HttpVersion httpVersion, final HttpStatus httpStatus) {
        this.httpVersion = httpVersion;
        this.httpStatus = httpStatus;
    }

    HttpVersion getHttpVersion() {
        return httpVersion;
    }

    void setHttpVersion(final HttpVersion httpVersion) {
        this.httpVersion = httpVersion;
    }

    HttpStatus getHttpStatus() {
        return httpStatus;
    }

    void setHttpStatus(final HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s\n", httpVersion.getHttpVersion(), httpStatus.getCode(), httpStatus.getPhrase());
    }
}
