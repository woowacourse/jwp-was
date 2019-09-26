package webserver.response;

import java.util.Arrays;

public enum MediaType {
    ALL_VALUE("*", "*/*"),
    IMAGE_GIF_VALUE("gif", "image/gif"),
    IMAGE_JPEG_VALUE("jpeg", "image/jpeg"),
    IMAGE_PNG_VALUE("png", "image/png"),
    TEXT_HTML_VALUE("html", "text/html"),
    TEXT_XML_VALUE("xml", "text/xml"),
    TEXT_CSS_VALUE("css", "text/css"),
    TEXT_JAVASCRIPT_VALUE("js", "text/js"),
    FONT_WOFF("woff", "application/x-font-woff"),
    FONT_TTF("ttf", "application/x-font-ttf"),
    IMAGE_X_ICON_VALUE("ico", "image/x-icon"),
    DEFAULT("**", "application/octet-stream");

    private String extension;
    private String mediaType;

    MediaType(String extension, String mediaType) {
        this.extension = extension;
        this.mediaType = mediaType;
    }

    public static MediaType of(String extension) {
        return Arrays.stream(MediaType.values())
                .filter(e -> e.contains(extension))
                .findFirst()
                .orElse(DEFAULT);
    }

    private boolean contains(String extension) {
        return this.extension.equals(extension);
    }

    public String getMediaType() {
        return mediaType;
    }
}
