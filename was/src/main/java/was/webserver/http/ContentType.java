package was.webserver.http;

import java.util.Arrays;

public enum ContentType {
    HTML("text/html;charset=utf-8", "./templates", ".html"),
    ICO("image/x-icon;charset=utf-8", "./templates", ".ico"),
    CSS("text/css;charset=utf-8", "./static", ".css"),
    JAVASCRIPT("text/javascript;charset=utf-8", "./static", ".js"),
    PNG("image/jpeg;charset=utf-8", "./static", ".png"),
    TTF("application/font-ttf;charset=utf-8", "./static", ".ttf"),
    WOFF("application/font-woff;charset=utf-8", "./static", ".woff");

    private final String contentType;
    private final String directory;
    private final String fileExtender;

    ContentType(String contentType, String directory, String fileExtender) {
        this.contentType = contentType;
        this.directory = directory;
        this.fileExtender = fileExtender;
    }

    public static ContentType from(String path) {
        return Arrays.stream(values())
                .filter(contentType -> path.endsWith(contentType.fileExtender))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getDirectory() {
        return directory;
    }

    public String getContentType() {
        return contentType;
    }
}
