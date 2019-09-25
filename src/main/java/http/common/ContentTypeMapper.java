package http.common;

import http.common.exception.InvalidContentTypeException;

import static utils.StringUtils.BLANK;

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

    private static final char DOT = '.';
    private static final int NOT_EXIST_EXTENSION_START_INDEX = 0;
    private final String contentType;

    ContentTypeMapper(String contentType) {
        this.contentType = contentType;
    }

    public static String getContentType(String path) {
        try {
            return valueOf(extractExtension(path).toUpperCase()).contentType;
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidContentTypeException();
        }
    }

    private static String extractExtension(String path) {
        int extensionStartIndex = path.lastIndexOf(DOT) + 1;
        return (extensionStartIndex == NOT_EXIST_EXTENSION_START_INDEX) ? BLANK : path.substring(extensionStartIndex);
    }

    public String getContentType() {
        return contentType;
    }
}
