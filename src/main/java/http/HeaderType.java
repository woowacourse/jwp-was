package http;

import java.util.Arrays;

public enum HeaderType {
    HOST("Host"),
    CONNECTION("Connection"),
    ACCEPT("Accept"),
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    LOCATION("Location");

    private final String typeName;

    HeaderType(String typeName) {
        this.typeName = typeName;
    }

    public static HeaderType from(String token) {
        return Arrays.stream(values())
            .filter(headerType -> headerType.typeName.equals(token))
            .findFirst().orElseThrow(() -> new IllegalArgumentException("지원하지 않는 http header 입니다."));
    }

    public String getTypeName() {
        return typeName;
    }
}
