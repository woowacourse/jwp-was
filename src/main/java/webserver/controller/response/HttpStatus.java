package webserver.controller.response;

public enum HttpStatus {
    OK("OK", 200),
    FOUND("FOUND", 302),
    BAD_REQUEST("BAD REQUEST", 400);

    private final String status;
    private final int statusCode;

    HttpStatus(String status, int statusCode) {
        this.status = status;
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
