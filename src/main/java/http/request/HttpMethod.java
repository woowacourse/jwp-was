package http.request;

public enum HttpMethod {
    GET,
    POST,
    PUT,
    DELETE;

    public boolean isPost() {
        return POST.equals(this);
    }
}
