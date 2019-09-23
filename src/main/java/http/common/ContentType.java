package http.common;

import http.common.exception.InvalidContentTypeException;

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
    ICO("image/x-icon"),
    FORM_URLENCODED("application/x-www-form-urlencoded");

    private final String contentType;

    ContentType(String contentType) {
        this.contentType = contentType;
    }

    public static String getContentType(String extension) {
        try {
            return valueOf(extension.toUpperCase()).contentType;
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidContentTypeException();
        }
    }

    public String getContentType() {
        return contentType;
    }
}
