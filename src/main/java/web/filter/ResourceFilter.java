package web.filter;

import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

public enum ResourceFilter {
    HTML(".html", "./templates", "text/html;charset=UTF-8"),
    JAVASCRIPT(".js", "./static", "application/javascript;charset=UTF-8"),
    CSS(".css", "./static", "text/css;charset=UTF-8");

    private final String suffix;
    private final String resourcePath;
    private final String contentType;

    ResourceFilter(final String suffix, final String resourcePath, final String contentType) {
        this.suffix = suffix;
        this.resourcePath = resourcePath;
        this.contentType = contentType;
    }

    public static ResourceFilter findResourceFilter(String requestPath) {
        return Arrays.stream(values())
                .filter(resourceFilter -> requestPath.endsWith(resourceFilter.suffix))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    public static boolean isResource(String requestPath) {
        return Arrays.stream(values())
                .anyMatch(resourceFilter -> requestPath.endsWith(resourceFilter.suffix));
    }

    public byte[] getBody(String requestPath) throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath(this.resourcePath + requestPath);
    }

    public String getContentType() {
        return contentType;
    }
}
