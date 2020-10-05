package model;

import java.util.Arrays;

public enum ContentType {

    HTML("text/html", ".html"),
    CSS("text/css", ".css"),
    JS("text/javascript", ".js"),
    WOFF("text/font", ".woff"),
    TTF("text/font", ".ttf"),
    ICO("image/x-icon", ".ico");

    private final String contentTypeValue;
    private final String extension;

    ContentType(String contentTypeValue, String extension) {
        this.contentTypeValue = contentTypeValue;
        this.extension = extension;
    }

    public static ContentType of(String extension) {
        return Arrays.stream(values())
            .filter(c -> c.extension.equals(extension))
            .findFirst()
            .orElse(null);
    }

    public String getContentTypeValue() {
        return contentTypeValue;
    }
}
