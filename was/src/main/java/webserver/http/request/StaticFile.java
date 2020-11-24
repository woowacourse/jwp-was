package webserver.http.request;

import java.util.Arrays;

public enum StaticFile {
    CSS(".css", "text/css"),
    JS(".js", "text/javascript"),
    EOT(".eot", "application/vnd.ms-fontobject"),
    SVG(".svf", "image/svg+xml"),
    TTF(".ttf", "font/ttf"),
    WOFF(".woff", "font/woff"),
    WOFF2(".woff2", "font/woff2"),
    PNG(".png", "image/png"),
    ICO(".ico", "image/x-icon");

    private final String suffix;
    private final String contentType;

    StaticFile(String suffix, String contentType) {
        this.suffix = suffix;
        this.contentType = contentType;
    }

    public static boolean isStatic(String uri) {
        return Arrays.stream(values())
                .anyMatch(value -> uri.endsWith(value.suffix));
    }

    public static StaticFile of(String uri) {
        return Arrays.stream(values())
                .filter(value -> uri.endsWith(value.suffix))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 파일입니다. : " + uri));
    }

    public String getContentType() {
        return contentType;
    }
}
