package http;

import java.util.Arrays;

public enum MimeType {
    HTML("html", "text/html"),
    CSS("css", "text/css"),
    JS("js", "text/javascript"),
    ICO("ico", "image/x-icon"),
    TTF("ttf", "font/ttf"),
    WOFF("woff", "font/woff");

    public static final String DELIMITER = "\\.";
    private final String extension;
    private final String contentType;

    MimeType(String extension, String contentType) {
        this.extension = extension;
        this.contentType = contentType;
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
        return ICO == this || HTML == this;
    }

    public String getContentType() {
        return contentType;
    }
}
