package http.response;

import http.common.HttpVersion;

public class StatusLine {

    private HttpStatus httpStatus;
    private HttpVersion httpVersion;

    public void setStatusLine(HttpStatus httpStatus, HttpVersion httpVersion) {
        this.httpStatus = httpStatus;
        this.httpVersion = httpVersion;
    }

    public void setOk(HttpVersion httpVersion) {
        this.httpStatus = HttpStatus.OK;
        this.httpVersion = httpVersion;
    }

    public void setRedirect(HttpVersion httpVersion) {
        this.httpStatus = HttpStatus.FOUND;
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
