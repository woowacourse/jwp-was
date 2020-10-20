package webserver.http;

import java.util.Arrays;

public enum FileType {
    HTML(".html", "text/html"),
    ICO(".ico", "image/x-icon"),
    CSS(".css", "text/css"),
    JS(".js", "text/javascript"),
    EOT(".eot", "application/vnd.ms-fontobject"),
    SVG(".svf", "image/svg+xml"),
    TTF(".ttf", "font/ttf"),
    WOFF(".woff", "font/woff"),
    WOFF2(".woff2", "font/woff2"),
    PNG(".png", "image/png");

    private final String suffix;
    private final String contentType;

    FileType(String suffix, String contentType) {
        this.suffix = suffix;
        this.contentType = contentType;
    }

    public static FileType of(String uri) {
        return Arrays.stream(values())
                .filter(value -> uri.endsWith(value.suffix))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 파일입니다. : " + uri));
    }

    public String getClassPath() {
        if (isTemplate()) {
            return "./templates";
        }
        if (isStatic()) {
            return "./static";
        }
        throw new IllegalArgumentException("지원하지 않는 형태의 파일입니다.");
    }

    private boolean isTemplate() {
        return this == HTML || this == ICO;
    }

    private boolean isStatic() {
        return
                this == CSS ||
                        this == JS ||
                        this == EOT ||
                        this == SVG ||
                        this == TTF ||
                        this == WOFF ||
                        this == WOFF2 ||
                        this == PNG;
    }

    public String getContentType() {
        return contentType;
    }
}
