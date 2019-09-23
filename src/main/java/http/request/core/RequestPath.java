package http.request.core;

public class RequestPath {
    private RequestPrefixPath requestPrefixPath;
    private String url;

    public RequestPath(RequestPrefixPath requestPrefixPath, String url) {
        this.requestPrefixPath = requestPrefixPath;
        this.url = url;
    }

    public String getFullPath() {
        return requestPrefixPath.getPathPrefix() + url;
    }
}
