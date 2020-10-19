package webserver.http.header.cookie;

public enum HttpCookieOptionName {
    PATH("Path");

    private final String name;

    HttpCookieOptionName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
