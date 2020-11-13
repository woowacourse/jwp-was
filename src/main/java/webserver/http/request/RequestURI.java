package webserver.http.request;

public class RequestURI {
    private final String uri;
    private final HttpParams httpParams;

    public RequestURI(String uri, HttpParams httpParams) {
        this.uri = uri;
        this.httpParams = httpParams;
    }

    public String getClassPath() {
        return FileType.of(uri).getClassPath() + uri;
    }

    public String getContentType() {
        return FileType.of(uri).getContentType();
    }

    public String getUri() {
        return uri;
    }

    public HttpParams getHttpParams() {
        return httpParams;
    }
}
