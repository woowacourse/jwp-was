package webserver.domain.response;

import webserver.domain.common.HttpVersion;

public class ResponseLine {
    private static final String NEW_LINE = "\r\n";

    private HttpVersion httpVersion;
    private HttpStatus httpStatus;

    public ResponseLine(HttpVersion httpVersion, HttpStatus httpStatus) {
        this.httpVersion = httpVersion;
        this.httpStatus = httpStatus;
    }

    public String getHttpVersion() {
        return httpVersion.getVersion();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public byte[] toBytes() {
        return (httpVersion.getVersion() + " " + httpStatus.toString() + NEW_LINE).getBytes();
    }
}
