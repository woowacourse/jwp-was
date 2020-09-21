package webserver;

public enum HttpStatus {
    OK("200 OK"),
    FOUND("302 FOUND");

    private final String code;

    HttpStatus(String code) {
        this.code = code;
    }

}
