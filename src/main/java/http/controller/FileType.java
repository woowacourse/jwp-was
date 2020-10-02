package http.controller;

import java.util.Arrays;

public enum FileType {
    HTML("html", "text/html;charset=utf-8", "./templates"),
    CSS("css", "text/css", "./static"),
    JS("js", "application/javascript", "./static"),
    ICO("ico", "image/icon", "./templates"),
    SVG("svg", "image/svg+xml", "./static"),
    WOFF("woff", "font/woff", "./static"),
    WOFF2("woff2", "font/woff2", "./static"),
    PLAINTEXT("", "text/plain", "./static");

    private String extension;
    private String contentType;
    private String rootPath;

    FileType(String extension, String contentType, String rootPath) {
        this.extension = extension;
        this.contentType = contentType;
        this.rootPath = rootPath;
    }

    public static FileType of(String extension) {
        return Arrays.stream(values())
                .filter(it -> it.extension.equals(extension))
                .findFirst()
                .orElse(FileType.PLAINTEXT);
    }

    public String getContentType() {
        return contentType;
    }

    public String getRootPath() {
        return rootPath;
    }
}
