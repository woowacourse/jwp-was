package web;

import java.util.Arrays;

public enum ResourceType {
    HTML(".html", "./templates", "text/html;charset=UTF-8"),
    CSS(".css", "./static", "text/css;charset=UTF-8"),
    JS(".js", "./static", "application/javascript;charset=UTF-8"),
    ICO(".ico", "./templates", "*/*"),
    AVG(".avg", "./static", "*/*"),
    TTF(".ttf", "./static", "*/*"),
    WOFF(".woff", "./static", "*/*"),
    WOFF2(".woff2", "./static", "*/*");

    private final String extension;
    private final String baseDirectory;
    private final String contentType;

    ResourceType(String extension, String baseDirectory, String contentType) {
        this.extension = extension;
        this.baseDirectory = baseDirectory;
        this.contentType = contentType;
    }

    public static boolean isStaticFile(String path) {
        return Arrays.stream(ResourceType.values())
                .anyMatch(resourceType -> path.contains(resourceType.extension));
    }

    public static ResourceType from(String path) {
        return Arrays.stream(ResourceType.values())
                .filter(resourceType -> path.contains(resourceType.extension))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("요청에 해당하는 정적 자원이 타입이 없습니다."));
    }

    public String getExtension() {
        return extension;
    }

    public String getBaseDirectory() {
        return baseDirectory;
    }

    public String getContentType() {
        return contentType;
    }
}
