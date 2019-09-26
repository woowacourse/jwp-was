package webserver.http.request.core;

public class RequestPath {
    private static final String INDEX_URL = "/index.html";

    private RequestPrefixPath requestPrefixPath;
    private String path;

    public RequestPath(RequestPrefixPath requestPrefixPath, String path) {
        this.requestPrefixPath = requestPrefixPath;
        this.path = parsingIndexUrl(path);
    }

    public RequestPath(String path) {
        this.requestPrefixPath = RequestPrefixPath.UNDEFINED;
        this.path = path;
    }

    private String parsingIndexUrl(String path) {
        return "/".equals(path) ? INDEX_URL : path;
    }

    public String getPath() {
        return path;
    }

    public String getFullPath() {
        return requestPrefixPath.getPathPrefix() + path;
    }
}
