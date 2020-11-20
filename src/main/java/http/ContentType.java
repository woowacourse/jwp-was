package http;

import java.util.Objects;

public enum ContentType {
    HTML("html", "text/html;charset=utf-8"),
    CSS("css", "text/css;charset=utf-8"),
    JS("js", "text/javascript;charset=utf-8"),
    ICO("ico", "image/icon"),
    PNG("png", "image/jpeg"),
    TTF("ttf", "application/font-ttf"),
    WOFF("woff", "application/font-woff");

    private String extension;
    private String contentType;

    ContentType(String extension, String contentType) {
        this.extension = extension;
        this.contentType = contentType;
    }

    public static ContentType findByURI(String uri) {
        if (Objects.isNull(uri) || uri.isEmpty()) {
            throw new IllegalArgumentException("잘못된 리소스입니다.");
        }
        String extension = uri.substring(uri.lastIndexOf(".") + 1);
        return findByExtension(extension);
    }

    private static ContentType findByExtension(String extension) {
        try {
            return valueOf(extension.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("잘못된 확장자입니다.");
        }
    }

    public String getContentType() {
        return contentType;
    }
}
