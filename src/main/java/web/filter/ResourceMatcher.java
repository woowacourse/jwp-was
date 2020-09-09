package web.filter;

import java.util.Arrays;

public enum ResourceMatcher {
    HTML(".html", "./templates", "text/html;charset=UTF-8"),
    JAVASCRIPT(".js", "./static", "application/javascript;charset=UTF-8"),
    CSS(".css", "./static", "text/css;charset=UTF-8");

    private final String suffix;
    private final String resourcePath;
    private final String contentType;

    ResourceMatcher(final String suffix, final String resourcePath, final String contentType) {
        this.suffix = suffix;
        this.resourcePath = resourcePath;
        this.contentType = contentType;
    }

    public static ResourceMatcher findResourceMatcher(String requestPath) {
        return Arrays.stream(values())
                .filter(resourceMatcher -> requestPath.endsWith(resourceMatcher.suffix))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("%s :일치하는 확장자가 존재하지 않습니다.", requestPath)));
    }

    public static boolean isResource(String requestPath) {
        return Arrays.stream(values())
                .anyMatch(resourceMatcher -> requestPath.endsWith(resourceMatcher.suffix));
    }

    public String getContentType() {
        return contentType;
    }

    public String getResourcePath() {
        return resourcePath;
    }
}