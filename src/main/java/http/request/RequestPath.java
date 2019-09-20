package http.request;

public class RequestPath {
    private final String path;

    RequestPath(String path) {
        this.path = (path.contains("/css") || path.contains("/js") || path.contains("/fonts")) ?
                "../resources/static" + path : "../resources/templates" + path;
    }

    public String getPath() {
        return path;
    }
}
