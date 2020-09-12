package web;

import java.util.Arrays;

public enum ResourceMatcher {
    HTML(".html", "./templates"),
    CSS(".css", "./static"),
    JS(".js", "./static"),
    ICO(".ico", "./templates"),
    AVG(".avg", "./static"),
    TTF(".ttf", "./static"),
    WOFF(".woff", "./static"),
    WOFF2(".woff2", "./static");

    private final String extension;
    private final String baseDirectory;

    ResourceMatcher(String extension, String baseDirectory) {
        this.extension = extension;
        this.baseDirectory = baseDirectory;
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
}
