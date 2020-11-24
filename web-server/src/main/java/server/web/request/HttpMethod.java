package server.web.request;

public enum HttpMethod {
    GET,
    POST;

    public boolean isGet() {
        return this == GET;
    }

    public boolean isPost() {
        return this == POST;
    }
}
