package webserver;

public class RequestPath {
    private static final String ROOT_PATH = "/";
    private static final String INDEX_PATH = "/index";
    private static final String INDEX_SUFFIX = ".html";

    private final String requestPath;

    public RequestPath(String requestPath) {
        if (ROOT_PATH.equals(requestPath)) {
            this.requestPath = INDEX_PATH + INDEX_SUFFIX;
            return;
        }
        this.requestPath = requestPath;
    }

    public String getRequestPath() {
        return requestPath;
    }
}
