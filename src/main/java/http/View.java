package http;

import java.nio.file.Path;

public class View {
    private Path path;

    public View(Path path) {
        this.path = path;
    }

    public View(String path) {

    }

    public Path getPath() {
        return path;
    }
}
