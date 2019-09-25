package webserver.http.response;

import webserver.http.common.HttpVersion;

public class StartLine {
    private HttpVersion httpVersion;
    private HttpStatus httpStatus;

    public StartLine(final HttpVersion httpVersion, final HttpStatus httpStatus) {
        this.httpVersion = httpVersion;
        this.httpStatus = httpStatus;
    }

    @Override
    public String toString() {
        return httpVersion + " " + httpStatus;
    }
}
