package webserver.http.request;

import exception.FileNotReadableException;
import exception.HttpResourceTypeNotFoundException;
import utils.FileIoUtils;

import java.util.Arrays;
import java.util.Objects;

public enum HttpResourceType {
    NONE("", "", ""),
    HTML("html", HttpResourceType.TEMPLATES_PATH, "text/html"),
    CSS("css", HttpResourceType.STATIC_PATH, "text/css"),
    EOT("eot", HttpResourceType.STATIC_PATH, "application/vnd.ms-fontobject"),
    SVG("svg", HttpResourceType.STATIC_PATH, "image/svg+xml"),
    TTF("ttf", HttpResourceType.STATIC_PATH, "font/ttf"),
    WOFF("woff", HttpResourceType.STATIC_PATH, "font/woff"),
    WOFF2("woff2", HttpResourceType.STATIC_PATH, "font/woff2"),
    PNG("png", HttpResourceType.STATIC_PATH, "image/png"),
    JS("js", HttpResourceType.STATIC_PATH, "application/javascript"),
    ICO("ico", HttpResourceType.TEMPLATES_PATH, "image/x-icon");

    private static final String STATIC_PATH = "./static";
    private static final String TEMPLATES_PATH = "./templates";
    private final String extension;
    private final String path;
    private final String contentType;

    HttpResourceType(String extension, String path, String contentType) {
        this.extension = extension;
        this.path = path;
        this.contentType = contentType;
    }

    public static HttpResourceType from(String fileExtension) {
        Objects.requireNonNull(fileExtension);

        return Arrays.stream(HttpResourceType.values())
                .filter(httpResourceType -> httpResourceType.extension.equals(fileExtension))
                .findFirst()
                .orElseThrow(() -> new HttpResourceTypeNotFoundException(fileExtension));
    }

    public byte[] readFile(String uri) {
        String filePath = this.path + uri;

        try {
            return FileIoUtils.loadFileFromClasspath(filePath);
        } catch (Exception e) {
            throw new FileNotReadableException(filePath);
        }
    }

    public String getContentType() {
        return this.contentType;
    }
}
