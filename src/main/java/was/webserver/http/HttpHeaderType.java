package was.webserver.http;

import java.util.Arrays;

public enum HttpHeaderType {
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    LOCATION("Location"),
    CONNECTION("Connection"),
    SET_COOKIE("Set-Cookie"),
    COOKIE("Cookie");

    private final String type;

    HttpHeaderType(String type) {
        this.type = type;
    }

    public static HttpHeaderType from(String type) {
        return Arrays.stream(values())
                .filter(httpHeaderType -> httpHeaderType.type.contains(type))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getType() {
        return type;
    }
}
