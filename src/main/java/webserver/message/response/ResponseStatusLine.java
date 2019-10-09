package webserver.message.response;

import webserver.message.HttpStatus;
import webserver.message.HttpVersion;

public class ResponseStatusLine {
    private static final String NEW_LINE = "\r\n";

    private HttpVersion httpVersion;
    private HttpStatus httpStatus;

    public ResponseStatusLine(HttpVersion httpVersion, HttpStatus httpStatus) {
        this.httpVersion = httpVersion;
        this.httpStatus = httpStatus;
    }

    public String getHttpVersion() {
        return httpVersion.getVersion();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    protected void setHttpVersion(HttpVersion httpVersion) {
        this.httpVersion = httpVersion;
    }

    protected void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public byte[] toBytes() {
        return (httpVersion.getVersion() + " " + httpStatus.toString() + NEW_LINE).getBytes();
    }
}
