package http;

import java.util.Arrays;

public enum MimeType {
    HTML("html", "text/html"),
    CSS("css", "text/css"),
    JS("js", "*/*"),
    ICO("ico", "image/x-icon"),
    TTF("ttf", "*/*"),
    WOFF("woff", "*/*");

    private final String extension;
    private final String contentType;

    MimeType(String extension, String contentType) {
        this.extension = extension;
        this.contentType = contentType;
    }

    public static MimeType from(String url) {
        String[] tokens = url.split("\\.");
        String urlExtension = tokens[tokens.length - 1];
        return Arrays.stream(values())
            .filter(mimeType -> mimeType.extension.equals(urlExtension))
            .findFirst().orElse(MimeType.HTML);
    }

    public String getFilePath(String url) {
        if (this.isTemplate()) {
            return "./templates" + url;
        }
        if (this.isStatic()) {
            return "./static" + url;
        }
        return "";
    }

    private boolean isTemplate() {
        return ICO.equals(this) || HTML.equals(this);
    }

    private boolean isStatic() {
        return !isTemplate();
    }

    public String getContentType() {
        return contentType;
    }
}
