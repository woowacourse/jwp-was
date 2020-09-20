package webserver.http.response;

public enum HttpStatus {
    OK("200"),
    FOUND("302"),
    NOT_FOUND("404"),
    INTERNAL_SERVER_ERROR("500");

    private static final String EMPTY_SPACE = " ";

    private String statusCode;

    HttpStatus(String statusCode) {
        this.statusCode = statusCode;
    }

    public String convertToString() {
        return statusCode + EMPTY_SPACE + name().replace("_", EMPTY_SPACE);
    }
}
