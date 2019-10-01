package webserver.http.response.core;

import webserver.http.HttpVersion;

public class ResponseStatusLine {
    private static final int STATUS_OK = 200;
    private static final String BLANK = " ";
    private static final String CRLF = "\r\n";

    private HttpVersion httpVersion;
    private ResponseStatus responseStatus;

    public ResponseStatusLine(HttpVersion httpVersion) {
        this.httpVersion = httpVersion;
        this.responseStatus = ResponseStatus.of(STATUS_OK);
    }

    public ResponseStatusLine(HttpVersion httpVersion, ResponseStatus responseStatus) {
        this.httpVersion = httpVersion;
        this.responseStatus = responseStatus;
    }

    public int getResponseStatusCode() {
        return responseStatus.getHttpStatusCode();
    }

    public String getStatusLine() {
        return httpVersion.getVersion()
                + BLANK
                + responseStatus.getHttpStatusCode()
                + BLANK
                + responseStatus.getHttpStatus()
                + CRLF;
    }
}
