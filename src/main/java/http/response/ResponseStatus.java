package http.response;

public enum ResponseStatus {
    OK(200),
    FOUND(302),
    NOT_FOUND(404),
    METHOD_NOT_ALLOWED(405),
    INTERNAL_SERVER_ERROR(500);

    private static final String RESPONSE_STATUS_FORMAT = "%s %s";
    private final int statusCode;

    ResponseStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String serialize() {
        return String.format(RESPONSE_STATUS_FORMAT, String.valueOf(statusCode), this.name());
    }
}
