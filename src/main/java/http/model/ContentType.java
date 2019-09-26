package http.model;

import http.controller.NotFoundException;

import java.util.Arrays;

public enum ContentType {
    HTML("text/html"),
    CSS("text/css"),
    JS("application/js"),
    PNG("image/png"),
    TTF("application/x-font-ttf"),
    WOFF("application/x-font-woff"),
    ICO("image/x-icon");


    private String type;

    ContentType(String type) {
        this.type = type;
    }

    public static ContentType of(String type) {
        return Arrays.stream(ContentType.values())
                .filter(value -> type.toUpperCase().equals(value.name()))
                .findAny()
                .orElseThrow(NotFoundException::new);
    }

    public String getType() {
        return this.type;
    }
}
