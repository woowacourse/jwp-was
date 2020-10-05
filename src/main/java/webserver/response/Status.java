package webserver.response;

public enum Status implements HttpField {
    OK("200 OK"),
    FOUND("302 FOUND"),
    NOT_FOUNT("404 NOT FOUND"),
    METHOD_NOT_ALLOWED("405 METHOD NOT ALLOWED");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    @Override
    public String get() {
        return name;
    }
}
