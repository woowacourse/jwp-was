package webserver;

public enum ContentType {
    JS("text/javascript"),
    CSS("text/css"),
    HTML("text/html"),
    PNG("image/png"),
    EOT("application/vnd.ms-fontobject"),
    SVG("image/svg+xml"),
    TTF("application/x-font-ttf"),
    WOFF("application/x-font-woff"),
    WOFF2("application/font-woff2"),
    ICO("image/x-icon");

    private final String contentType;

    ContentType(String contentType) {
        this.contentType = contentType;
    }

    public static String getContentType(String extension) {
        return ContentType.valueOf(extension.toUpperCase()).contentType;
    }
}
