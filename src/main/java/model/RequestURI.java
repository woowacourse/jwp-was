package model;

public class RequestURI {
    private final String uri;
    private final HttpParams httpParams;

    public RequestURI(String uri, HttpParams httpParams) {
        this.uri = uri;
        this.httpParams = httpParams;
    }

    public String getUri() {
        return uri;
    }

    public HttpParams getHttpParams() {
        return httpParams;
    }
}
