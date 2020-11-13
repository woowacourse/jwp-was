package webserver.http.request;

import webserver.http.FileType;

public class RequestURI {
    private static final String SLASH = "/";

    private final String uri;
    private final HttpParams httpParams;

    public RequestURI(String uri, HttpParams httpParams) {
        this.uri = uri;
        this.httpParams = httpParams;
    }

    public String getClassPath() {
        return FileType.of(uri).getClassPath() + SLASH + uri;
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
