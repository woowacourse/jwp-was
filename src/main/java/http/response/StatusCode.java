package http.response;

public enum StatusCode {
    OK("OK", 200),
    FOUND("FOUND", 302),
    BAD_REQUEST("BAD REQUEST", 400),
    NOT_FOUND("NOT FOUND", 404),
    METHOD_NOT_FOUND("METHOD NOT FOUND", 405),
    INTERNAL_SERVER_ERROR("INTERNAL SERVER ERROR", 500);

    private final String status;
    private final int value;

    StatusCode(String status, int value) {
        this.status = status;
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public int getStatusValue() {
        return value;
    }
}
