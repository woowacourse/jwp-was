package http.model.response;

import http.exceptions.IllegalHttpRequestException;

import java.util.Arrays;

public enum ContentType {
    HTML("text/html"),
    CSS("text/css"),
    JS("application/js"),
    PNG("image/png");

    private String mimeType;

    ContentType(String mimeType) {
        this.mimeType = mimeType;
    }

    public static ContentType of(String extension) {
        try {
            return ContentType.valueOf(extension.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalHttpRequestException(e.getMessage());
        }
    }

    public static ContentType from(String mime) {
        return Arrays.stream(ContentType.values())
                .filter(contentType -> contentType.mimeType.equals(mime))
                .findAny()
                .orElseThrow(IllegalHttpRequestException::new);
    }

    public boolean isHTML() {
        return this == ContentType.HTML;
    }

    public String getMimeType() {
        return this.mimeType;
    }
}
