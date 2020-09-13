package response;

public enum StatusCode {
    
    OK(200, "OK"),
    CREATED(201, "Created"),
    FOUND(302, "Found");

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
