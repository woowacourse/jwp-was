package http;

public enum HttpStatus {
    OK("200", "OK"),
    FOUND("302", "FOUND"),
    NOT_FOUND("404", "NOT FOUND"),
    METHOD_NOT_ALLOW("405", "Method Not Allowed");

    private String statusCode;
    private String reasonPhrase;

    HttpStatus(String statusCode, String reasonPhrase) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }

    @Override
    public String toString() {
        return statusCode + " " + reasonPhrase;
    }
}
