package http.request;

import http.exception.NotSupportContentTypeException;

import java.util.Arrays;

public enum ContentType {
    FROM_URLENCODED("application/x-www-form-urlencoded");

    private final String type;

    ContentType(String type) {
        this.type = type;
    }

    public static ContentType of(String type) {
        return Arrays.stream(ContentType.values())
                .filter(contentType -> contentType.type.equals(type))
                .findFirst()
                .orElseThrow(NotSupportContentTypeException::new);
    }
}
