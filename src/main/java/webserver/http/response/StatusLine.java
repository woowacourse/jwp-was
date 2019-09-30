package webserver.http.response;

import webserver.http.HttpStatus;

public class StatusLine {
    private static final String SINGLE_BLANK = " ";
    private String version;
    private HttpStatus httpStatus;

    public StatusLine(String httpVersion) {
        this.version = httpVersion;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String toString() {
        return version + SINGLE_BLANK + httpStatus.getValue() + SINGLE_BLANK + httpStatus.getReasonPhrase();
    }
}

