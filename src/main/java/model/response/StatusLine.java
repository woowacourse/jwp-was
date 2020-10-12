package model.response;

import model.general.Status;
import model.request.Request;

public class StatusLine {

    private final String httpVersion;
    private final Status status;

    private StatusLine(String httpVersion, Status status) {
        this.httpVersion = httpVersion;
        this.status = status;
    }

    public static StatusLine of(Request request, Status status) {
        String httpVersion = request.getHttpVersion();

        return new StatusLine(httpVersion, status);
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
