package model;

import java.util.Arrays;
import java.util.Objects;

public enum ContentType {

    HTML("text/html", ".html"),
    CSS("text/css", ".css"),
    JS("text/javascript", ".js"),
    WOFF("text/font", ".woff"),
    TTF("text/font", ".ttf"),
    ICO("image/x-icon", ".ico");

    private final String contentType;
    private final String extension;

    ContentType(String contentType, String extension) {
        this.contentType = contentType;
        this.extension = extension;
    }

    public static ContentType of(String extension) {
        if(Objects.isNull(extension)){
            return null;
        }
        return Arrays.stream(values())
            .filter(c -> c.extension.equals(extension))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Not Exist ContentType"));
    }

    public String getContentType() {
        return contentType;
    }
}
