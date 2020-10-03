package http;

import java.util.Arrays;

public enum StaticFile {

    HTML(".html", Constants.TEMPLATES_FILE_PATH, "text/html;charset=utf-8"),
    ICO(".ico", Constants.TEMPLATES_FILE_PATH, "image/vnd.microsoft.icon"),
    CSS(".css", Constants.STATIC_FILE_PATH, "text/css"),
    JS(".js", Constants.STATIC_FILE_PATH, "text/javascript"),
    WOFF(".woff", Constants.STATIC_FILE_PATH, "text/woff"),
    PNG(".png", Constants.STATIC_FILE_PATH, "image/png"),
    JPEG(".jpeg", Constants.STATIC_FILE_PATH, "image/jpeg"),
    SVG(".svg", Constants.STATIC_FILE_PATH, "image/svg_xml");

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

    private static class Constants {
        private static final String STATIC_FILE_PATH = "./static";
        private static final String TEMPLATES_FILE_PATH = "./templates";
    }
}
