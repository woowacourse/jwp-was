package http.support;

public enum StatusCode {
    OK(200),
    FOUND(302),
    NOT_FOUND(404);

    private int statusCode;

    StatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
