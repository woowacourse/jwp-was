package webserver;

public enum Kind {
    API(null),
    WEBAPP_FILE("webapp"),
    STATIC_FILE("static");

    private String directory;

    Kind(String directory) {
        this.directory = directory;
    }

    public static Kind from(String urlPath) {
        return FileNameExtension.from(urlPath).getKind();
    }

    public String getDirectory() {
        return directory;
    }
}
