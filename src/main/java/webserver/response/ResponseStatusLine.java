package webserver.response;

import webserver.request.HttpRequest;

public class ResponseStatusLine {
    private static final String STATUS_LINE_SEPARATOR = " ";

    private final String httpVersion;
    private final String statusCode;
    private final String statusText;

    private ResponseStatusLine(String httpVersion, String statusCode, String statusText) {
        this.httpVersion = httpVersion;
        this.statusCode = statusCode;
        this.statusText = statusText;
    }

    public static ResponseStatusLine of(HttpRequest httpRequest, String statusCode, String statusText) {
        String version = httpRequest.getHttpVersion();
        return new ResponseStatusLine(version, statusCode, statusText);
    }

    public String response() {
        return httpVersion + STATUS_LINE_SEPARATOR + statusCode + STATUS_LINE_SEPARATOR + statusText + "\r\n";
    }
}
