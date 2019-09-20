package http.model;

import http.supoort.IllegalHttpRequestException;

import java.util.Arrays;

public enum ContentType {
    HTML("text/html"),
    CSS("text/css"),
    JS("application/js"),
    PNG("image/png");

    private String type;

    ContentType(String type) {
        this.type = type;
    }

    public static ContentType of(String extension) {
        try {
            return ContentType.valueOf(extension.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalHttpRequestException(e.getMessage());
        }
    }

    public static ContentType from(String type) {
        return Arrays.stream(ContentType.values())
                .filter(contentType -> contentType.type.equals(type))
                .findAny()
                .orElseThrow(IllegalHttpRequestException::new);
    }

    public boolean isHTML() {
        return this == ContentType.HTML;
    }

    public String getType() {
        return this.type;
    }
}
