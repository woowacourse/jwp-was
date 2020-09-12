package web;

public class Path {
    private final String path;

    public Path(String path) {
        this.path = path;
    }

    public boolean isStaticFile() {
        return ResourceMatcher.isStaticFile(this.path);
    }

    public String getPath() {
        return path;
    }
}
