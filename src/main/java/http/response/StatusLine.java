package http.response;

import http.common.HttpVersion;

public class StatusLine {

    public static final String STATUS_LINE_DELIMITER = " ";
    private HttpVersion httpVersion;
    private HttpStatus httpStatus;

    public StatusLine() {
    }

    public void setHttpVersion(final HttpVersion httpVersion) {
        this.httpVersion = httpVersion;
    }

    public void setHttpStatus(final HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpVersion getHttpVersion() {
        return this.httpVersion;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getVersionAndStatusString() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(httpVersion.getVersion())
            .append(STATUS_LINE_DELIMITER)
            .append(httpStatus.getCodeAndStatus())
            .toString();
    }
}
