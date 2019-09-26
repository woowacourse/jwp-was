package webserver.http.response;

public enum HttpVersion {
    HTTP1("HTTP/1.1"), HTTP2("HTTP/2.0");

    private final String name;

    HttpVersion(String name) {
        this.name = name;
    }
}
