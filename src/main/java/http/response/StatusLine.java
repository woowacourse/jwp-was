package http.response;

import http.HttpStatus;

public class StatusLine {
    private String version;
    private HttpStatus status;

    public StatusLine(String version, HttpStatus status) {
        this.version = version;
        this.status = status;
    }

    public String getStatusLineString() {
        return String.format("%s %d %s", version, status.getCode(), status.getMessage());
    }

    public void update(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}