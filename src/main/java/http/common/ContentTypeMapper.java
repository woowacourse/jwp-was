package http.common;

import http.common.exception.InvalidContentTypeException;

public enum ContentTypeMapper {
    JS(ContentType.JS),
    CSS(ContentType.CSS),
    HTML(ContentType.HTML),
    PNG(ContentType.PNG),
    EOT(ContentType.EOT),
    SVG(ContentType.SVG),
    TTF(ContentType.TTF),
    WOFF(ContentType.WOFF),
    WOFF2(ContentType.WOFF2),
    ICO(ContentType.ICO);

    private final String contentType;

    ContentTypeMapper(String contentType) {
        this.contentType = contentType;
    }

    public static String getContentType(String path) {
        try {
            String[] extension = path.split("[.]");
            return valueOf(extension[extension.length - 1].toUpperCase()).contentType;
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidContentTypeException();
        }
    }

    public String getContentType() {
        return contentType;
    }
}
