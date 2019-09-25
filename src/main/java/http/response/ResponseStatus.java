package http.response;

public enum ResponseStatus {
    OK(200, "OK"),
    FOUND(302, "Found"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private static final String RESPONSE_STATUS_FORMAT = "%d %s";
    private final int statusCode;
    private final String responsePhrase;

    ResponseStatus(int statusCode, String responsePhrase) {
        this.statusCode = statusCode;
        this.responsePhrase = responsePhrase;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String serialize() {
        return String.format(RESPONSE_STATUS_FORMAT, statusCode, responsePhrase);
    }
}
