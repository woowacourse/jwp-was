package web;

import exception.InvalidRequestPathException;

import java.util.Arrays;

public enum FileType {
    HTML(".html", "./templates", "text/html"),
    ICO(".ico", "./templates", "image/vnd.microsoft.icon"),
    CSS(".css", "./static", "text/css"),
    JS(".js", "./static", "text/javascript"),
    WOFF(".woff", "./static", "text/woff"),
    PNG(".png", "./static", "image/png"),
    JPEG(".jpeg", "./static", "image/jpeg"),
    SVG(".svg", "./static", "image/svg_xml");

    private final String type;
    private final String resourcePath;
    private final String contentType;

    FileType(String type, String resourcePath, String contentType) {
        this.type = type;
        this.resourcePath = resourcePath;
        this.contentType = contentType;
    }

    public static FileType findFileType(String path) {
        return Arrays.stream(FileType.values())
                .filter(fileType -> path.endsWith(fileType.getType()))
                .findAny()
                .orElseThrow(InvalidRequestPathException::new);
    }

    public String getType() {
        return type;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public String getContentType() {
        return contentType;
    }
}
