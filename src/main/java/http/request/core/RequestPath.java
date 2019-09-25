package http.request.core;

public class RequestPath {
    private static final String INDEX_URL = "/index.html";

    private RequestPrefixPath requestPrefixPath;
    private String url;

    public RequestPath(RequestPrefixPath requestPrefixPath, String url) {
        this.requestPrefixPath = requestPrefixPath;
        this.url = parsingIndexUrl(url);
    }

    private String parsingIndexUrl(String url) {
        return url.equals("/") ? INDEX_URL : url;
    }

    public String getFullPath() {
        return requestPrefixPath.getPathPrefix() + url;
    }
}
