package response;

public enum StatusCode {
    
    OK(200, "OK"),
    CREATED(201, "Created"),
    FOUND(302, "Found"),
    NOT_FOUND(404, "NotFound"),
    BAD_REQUEST(400, "Bad Request"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private int statusCode;
    private String statusMessage;

    StatusCode(int statusCode, String statusMessage) {
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
