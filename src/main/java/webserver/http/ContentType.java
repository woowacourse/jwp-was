package webserver.http;

import java.util.Arrays;

public enum ContentType {
    HTML("text/html;charset=utf-8", "./templates", ".html"),
    ICO("image/x-icon;charset=utf-8", "./templates", ".ico"),
    CSS("text/css;charset=utf-8", "./static", ".css"),
    JAVASCRIPT("text/javascript;charset=utf-8", "./static", ".js");

    private final String contentType;
    private final String directory;
    private final String fileExtender;

    ContentType(String contentType, String directory, String fileExtender) {
        this.contentType = contentType;
        this.directory = directory;
        this.fileExtender = fileExtender;
    }

    public static ContentType from(Url url) {
        return Arrays.stream(values())
                .filter(contentType -> url.isEndsWith(contentType.fileExtender))
                .findFirst()
                .orElse(HTML);
    }

    public String getDirectory() {
        return directory;
    }

    public String getContentType() {
        return contentType;
    }
}
