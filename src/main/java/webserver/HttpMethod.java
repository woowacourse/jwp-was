package webserver;

public enum HttpMethod {
    GET,
    POST,
    PUT,
    DELETE;

    public boolean isGet() {
        return this == GET;
    }

    public boolean isPost() {
        return this == POST;
    }

    public boolean isPut() {
        return this == PUT;
    }

    public boolean isDelete() {
        return this == DELETE;
    }
}
