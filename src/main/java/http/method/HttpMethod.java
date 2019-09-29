package http.method;

public enum HttpMethod {
    GET,
    POST;

    public boolean isGet() {
        return this.equals(GET);
    }

    public boolean isPost() {
        return this.equals(POST);
    }
}
