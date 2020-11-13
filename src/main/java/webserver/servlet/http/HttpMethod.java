package webserver.servlet.http;

public enum HttpMethod {

    GET, POST;

    public boolean isGet() {
        return this == GET;
    }

    public boolean hasRequestBody() {
        return this == POST;
    }
}
