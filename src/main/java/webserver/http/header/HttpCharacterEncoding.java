package webserver.http.header;

public enum HttpCharacterEncoding {
    UTF_8("utf-8");

    private final String name;

    HttpCharacterEncoding(String name) {
        this.name = name;
    }

    public String toHttpMessage() {
        return "charset=" + this.name;
    }
}
