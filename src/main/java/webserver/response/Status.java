package webserver.response;

public enum Status {
    OK("200 OK"),
    FOUND("302 FOUND");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
