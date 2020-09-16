package http;

import java.util.stream.Stream;

public enum StaticResourceType {
    HTML(".html", "./templates", "text/html"),
    JS(".js", "./static", "text/javascript"),
    CSS(".css", "./static", "text/css"),
    SVG(".svg", "./static", "image/svg+xml"),
    PNG(".png", "./static", "image/png"),
    TTF(".ttf", "./static", "application/x-font-ttf"),
    WOFF(".woff", "./static", "application/x-font-woff"),
    WOFF2(".woff2", "./static", "application/x-font-woff2"),
    EOT(".eot", "./static", "application/vnd.ms-fontobject");

    private final String extension;
    private final String resourceLocation;
    private final String contentType;

    StaticResourceType(final String extension, final String resourceLocation, final String contentType) {
        this.extension = extension;
        this.resourceLocation = resourceLocation;
        this.contentType = contentType;
    }

    public static StaticResourceType findByUri(final Uri uri) {
        return Stream.of(values())
                .filter(staticResourceType -> uri.endsWith(staticResourceType.getExtension()))
                .findFirst()
                .orElseThrow(() -> new StaticResourceTypeNotFoundException("일치하는 정적 자원을 찾을 수 없습니다. " + uri.toString()));
    }

    public static boolean anyMatch(final Uri uri) {
        return Stream.of(values())
                .anyMatch(staticResourceType -> uri.endsWith(staticResourceType.getExtension()));
    }

    public String getExtension() {
        return extension;
    }

    public String getResourceLocation() {
        return resourceLocation;
    }

    public String getContentType() {
        return contentType;
    }
}
