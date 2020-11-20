package server.utils;

public class ResourcePath {
    private final String root;
    private final String requestPath;

    public ResourcePath(String root, String requestPath) {
        this.root = root;
        this.requestPath = requestPath;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public String getFilePath() {
        return root + requestPath;
    }
}
