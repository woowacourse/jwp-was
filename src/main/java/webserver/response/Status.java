package webserver.response;

public enum Status implements HttpField {
    OK("200 OK"),
    FOUND("302 FOUND");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    @Override
    public String get() {
        return name;
    }
}
