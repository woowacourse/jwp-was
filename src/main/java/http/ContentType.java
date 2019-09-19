package http;

public enum ContentType {
    HTML("text/html", "html"),
    CSS("text/css", "css"),
    JAVASCRIPT("application/js", "js");

    private final String contents;
    private final String fileName;

    ContentType(String contents, String fileName) {
        this.contents = contents;
        this.fileName = fileName;
    }

    public static ContentType valueByPath(String path) {
        String[] tokens = path.split("\\.");
        return valueByFileName(tokens[tokens.length-1]);
    }

    public static ContentType valueByFileName(String fileName) {
        for (ContentType value : ContentType.values()) {
            if (value.fileName.equals(fileName)) {
                return value;
            }
        }
        throw new IllegalArgumentException("지원하지 않는 확장자입니다.");
    }

    public String getContents() {
        return contents;
    }
}
