package http;

import http.exception.MediaTypeNotDefinedException;

import java.util.Arrays;

public enum MediaType {
    STYLESHEET("text/css"),
    JAVASCRIPT("application/javascript"),
    JSON("application/json"),
    HTML("text/html"),
    ALL("*/*");

    private final String value;

    MediaType(String value) {
        this.value = value;
    }

    public static MediaType of(String value) {
        return Arrays.stream(values())
                .filter(mediaType -> mediaType.value.equals(value))
                .findFirst()
                .orElseThrow(MediaTypeNotDefinedException::new);
    }

    public String getValue() {
        return this == ALL ? HTML.value : value;
    }
}
