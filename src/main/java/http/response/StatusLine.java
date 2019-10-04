package http.response;

import http.common.HttpStatus;

public class StatusLine {
    private static final String HTTP_1_1 = "HTTP/1.1";
    private static final String BLANK = " ";

    private String httpVersion;
    private HttpStatus httpStatus;

    public StatusLine(String httpVersion, HttpStatus httpStatus) {
        this.httpVersion = httpVersion;
        this.httpStatus = httpStatus;
    }

    public static StatusLine of() {
        return new StatusLine(HTTP_1_1, HttpStatus.OK);
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String toString() {
        return httpVersion + BLANK + httpStatus;
    }
}
