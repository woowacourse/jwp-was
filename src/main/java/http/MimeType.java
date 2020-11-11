package http;

import java.util.Arrays;

public enum MimeType {
    HTML("html", "text/html", "template"),
    CSS("css", "text/css", "static"),
    JS("js", "text/javascript", "static"),
    ICO("ico", "image/x-icon", "template"),
    TTF("ttf", "font/ttf", "static"),
    WOFF("woff", "font/woff", "static");

    public static final String DELIMITER = "\\.";
    private final String extension;
    private final String contentType;
    private final String fileType;

    MimeType(String extension, String contentType, String fileType) {
        this.extension = extension;
        this.contentType = contentType;
        this.fileType = fileType;
    }

    public static MimeType from(String url) {
        String[] tokens = url.split(DELIMITER);
        String urlExtension = tokens[tokens.length - 1];
        return Arrays.stream(values())
            .filter(mimeType -> mimeType.extension.equals(urlExtension))
            .findFirst().orElse(MimeType.HTML);
    }

    public String getFilePath(String url) {
        if (this.isTemplate()) {
            return "./templates" + url;
        }
        return "./static" + url;
    }

    private boolean isTemplate() {
        return this.fileType.equals("template");
    }

    public String getContentType() {
        return contentType;
    }
}
