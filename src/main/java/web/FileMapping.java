package web;

import java.util.Arrays;

public enum FileMapping {
    HTML(".html", "text/html;charset=utf-8", "./templates"),
    CSS(".css", "text/css;charset=utf-8", "./static"),
    JAVASCRIPT(".js", "text/javascript;charset=utf-8", "./static"),
    PNG(".png", "image/png", "./static"),
    EOT(".eot", "application/vnd.ms-fontobject", "./static"),
    WOFF(".woff", "application/x-font-woff", "./static"),
    TTF(".ttf", "application/x-font-ttf", "./static"),
    SVG(".svg", "image/svg+xml", "./static"),
    ICO(".ico", "image/x-icon", "./templates");

    private final String extension;
    private final String contentType;
    private final String filePath;

    FileMapping(String extension, String contentType, String filePath) {
        this.extension = extension;
        this.contentType = contentType;
        this.filePath = filePath;
    }

    public static FileMapping findByExtension(String url) {
        return Arrays.stream(values())
            .filter(filter -> url.contains(filter.extension))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("사용할 수 없는 확장자입니다."));
    }

    public String getContentType() {
        return contentType;
    }

    public String getFilePath() {
        return filePath;
    }
}
