package http;

import java.util.Arrays;

public enum ContentType {
    HTML(".html", "text/html"),
    CSS(".css", "text/css"),
    JS(".js", "text/javascript"),
    FONT(".woff", "application/x-font-woff");

    private final String suffix;
    private final String contentType;

    ContentType(String suffix, String contentType) {
        this.suffix = suffix;
        this.contentType = contentType;
    }

    public static ContentType of(String requestUrl) {
        return Arrays.stream(ContentType.values())
                .filter(value -> requestUrl.endsWith(value.getSuffix())).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 확장자입니다."));
    }

    public String getSuffix() {
        return suffix;
    }

    public String getContentType() {
        return contentType;
    }
}
