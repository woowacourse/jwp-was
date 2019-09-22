package webserver.controller.request.header;

import exception.ContentTypeNotFoundException;

import java.util.Arrays;

public enum ContentType {
    HTML("html"),
    CSS("css"),
    JAVASCRIPT("js"),
    ICO("ico"),
    TTF("ttf"),
    WOFF("woff");

    private final String contentType;

    ContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public static ContentType match(String url) {
        return Arrays.stream(ContentType.values())
            .filter(c -> url.contains("." + c.getContentType()))
            .findFirst()
            .orElseThrow(ContentTypeNotFoundException::new);
    }
}
