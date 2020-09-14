package web;

import java.util.Arrays;

public enum StaticFile {

    HTML(".html", "./templates", "text/html"),
    ICO(".ico", "./templates", "image/vnd.microsoft.icon"),
    CSS(".css", "./static", "text/css"),
    JS(".js", "./static", "text/javascript"),
    WOFF(".woff", "./static", "text/woff"),
    PNG(".png", "./static", "image/png"),
    JPEG(".jpeg", "./static", "image/jpeg"),
    SVG(".svg", "./static", "image/svg_xml");

    private final String fileType;
    private final String resourcePath;
    private final String contentType;

    StaticFile(String fileType, String resourcePath, String contentType) {
        this.fileType = fileType;
        this.resourcePath = resourcePath;
        this.contentType = contentType;
    }

    public static StaticFile findStaticFile(String requestPath) {
        return Arrays.stream(values())
            .filter(staticFile -> requestPath.endsWith(staticFile.fileType))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException(String.format("%s : 지원하지 않는 정적 파일 타입입니다.", requestPath)));
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public String getContentType() {
        return contentType;
    }
}
