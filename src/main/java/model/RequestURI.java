package model;

public class RequestURI {
    private final String uri;
    private final HttpQueryParams httpQueryParams;

    public RequestURI(String uri, HttpQueryParams httpQueryParams) {
        this.uri = uri;
        this.httpQueryParams = httpQueryParams;
    }

    public String getUri() {
        return uri;
    }

    public HttpQueryParams getHttpQueryParams() {
        return httpQueryParams;
    }
}
