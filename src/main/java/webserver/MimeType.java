package webserver;

public enum MimeType {
    HTML(".html", "text/html"),
    CSS(".css", "text/css"),
    JS(".js", "application/javascript"),
    PLAIN(".*", "text/plain");

    private String fileName;
    private String mimeType;

    MimeType(String fileName, String mimeType) {
        this.fileName = fileName;
        this.mimeType = mimeType;
    }

    public static MimeType values(String path) {
        for (MimeType value : values()) {
            if (path.endsWith(value.fileName)) {
                return value;
            }
        }
        return PLAIN;
    }

    public String getMimeType() {
        return mimeType;
    }
}
