package model.response;

import model.general.Status;
import model.request.HttpRequest;

public class StatusLine {

    private final String httpVersion;
    private final Status status;

    private StatusLine(String httpVersion, Status status) {
        this.httpVersion = httpVersion;
        this.status = status;
    }

    public static StatusLine of(Status status) {
        return new StatusLine("HTTP/1.1", status);
    }

    public static StatusLine of(HttpRequest httpRequest, Status status) {
        return new StatusLine(httpRequest.getHttpVersion(), status);
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public String getStatusCode() {
        return String.valueOf(status.getStatusCode());
    }

    public String getReasonPhrase() {
        return status.getReasonPhrase();
    }
}
