package http;

public enum ContentType {
    HTML("text/html", "html"),
    CSS("text/css", "css"),
    JAVASCRIPT("application/js", "js"),
    ICON("image/x-icon", "ico");


    private static final String PATH_DELIMITER = "\\.";

    private final String contents;
    private final String suffix;

    ContentType(String contents, String suffix) {
        this.contents = contents;
        this.suffix = suffix;
    }

    public static ContentType valueByPath(String path) {
        String[] tokens = path.split(PATH_DELIMITER);
        return valueByFileName(tokens[tokens.length-1]);
    }

    public static ContentType valueByFileName(String fileName) {
        for (ContentType value : ContentType.values()) {
            if (value.suffix.equals(fileName)) {
                return value;
            }
        }
        throw new IllegalArgumentException("지원하지 않는 확장자입니다.");
    }

    public String getContents() {
        return contents;
    }
}
