package webserver.http.header;

public enum HttpCookieOption {
    PATH("Path");

    private final String name;

    HttpCookieOption(String name) {
        this.name = name;
    }

    public String toHttpMessage(String value) {
        return ";" + this.name + "=" + value;
    }
}
