package http.response;

public enum ResponseStatus {
    OK(200, "OK"),
    FOUND(302, "Found"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed");

    private int statusCode;
    private String statusMessage;

    ResponseStatus(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
