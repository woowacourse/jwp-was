package http.model;

import http.supoort.IllegalHttpRequestException;

public enum ContentType {
    HTML("text/html"),
    CSS("text/css"),
    JS("application/js"),
    PNG("image/png");

    private String type;

    private ContentType(String type) {
        this.type = type;
    }

    public static ContentType of(String type) {
        try {
            return ContentType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalHttpRequestException(e.getMessage());
        }
    }

    public String getType() {
        return this.type;
    }
}
