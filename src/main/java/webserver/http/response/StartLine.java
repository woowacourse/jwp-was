package webserver.http.response;

import webserver.http.common.HttpVersion;

public class StartLine {
    private HttpVersion httpVersion;
    private HttpStatus httpStatus;

    public StartLine(final HttpVersion httpVersion) {
        this.httpVersion = httpVersion;
    }

    public void setHttpStatus(final HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String toString() {
        return httpVersion + " " + httpStatus;
    }
}
