package http.request;

public class Path {
    private String path;

    public Path(String path) {
        if ("/".equals(path)) {
            this.path = "/index.html";
            return;
        }
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return path;
    }
}