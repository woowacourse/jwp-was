package http.support;

public enum StatusCode {
    OK(200, "OK"),
    FOUND(302, "FOUND"),
    NOT_FOUND(404, "Not Found");

    private int statusCode;
    private String statusName;

    StatusCode(int statusCode, String statusName) {
        this.statusCode = statusCode;
        this.statusName = statusName;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusName() {
        return statusName;
    }
}
