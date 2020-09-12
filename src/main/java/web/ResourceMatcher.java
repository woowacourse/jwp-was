package web;

import java.util.Arrays;

public enum ResourceMatcher {
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

    ResourceMatcher(String extension, String baseDirectory, String contentType) {
        this.extension = extension;
        this.baseDirectory = baseDirectory;
        this.contentType = contentType;
    }

    public static boolean isStaticFile(String path) {
        return Arrays.stream(ResourceMatcher.values())
                .anyMatch(staticFile -> path.contains(staticFile.extension));
    }

    public static String findBaseDirectory(String path) {
        return Arrays.stream(ResourceMatcher.values())
                .filter(staticFile -> path.contains(staticFile.extension))
                .findFirst()
                .map(it -> it.baseDirectory)
                .orElseGet(() -> "/");
    }


    public static String findContentType(String path) {
        return Arrays.stream(ResourceMatcher.values())
                .filter(staticFile -> path.contains(staticFile.extension))
                .findFirst()
                .map(it -> it.contentType)
                .orElseGet(() -> "*/*");
    }
}
