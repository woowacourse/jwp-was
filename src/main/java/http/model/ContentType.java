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

    public static ContentType of(String type) {
        return Arrays.stream(ContentType.values())
                .filter(value -> type.toUpperCase().equals(value.name()))
                .findAny()
                .orElseThrow(IllegalHttpRequestException::new);
    }

    public String getType() {
        return this.type;
    }
}
