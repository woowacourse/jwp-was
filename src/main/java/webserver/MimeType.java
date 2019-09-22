package webserver;

public enum MimeType {
    HTML(".html", "text/html"),
    CSS(".css", "text/css"),
    JS(".js", "application/javascript");

    String fileName;
    String mimeType;

    MimeType(String fileName, String mimeType) {
        this.fileName = fileName;
        this.mimeType = mimeType;
    }

    public static String values(String path) {
        for (MimeType value : values()) {
            if (path.endsWith(value.fileName)) {
                return value.mimeType;
            }
        }
        return "plain/text";
    }
}
