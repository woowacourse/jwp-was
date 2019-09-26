package webserver.http.request;

public class RequestLine {
    private final RequestMethod method;
    private final RequestUri uri;

    public RequestLine(String method, String uri) {
        this.method = RequestMethod.valueOf(method);
        this.uri = new RequestUri(uri);
    }

    public RequestMethod getMethod() {
        return method;
    }

    public String getQueryString(String key) {
        return uri.getQueryString(key);
    }

    public String getAbsPath() {
        return uri.getAbsPath();
    }

    public RequestUri getUri() {
        return uri;
    }
}
