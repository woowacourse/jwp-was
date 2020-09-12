package utils;

import java.util.stream.Stream;

public enum AcceptType {
    HTML("./templates", "text/html"),
    CSS("./static", "text/css"),
    JS("./static", "text/js"),
    TTF("./static", "text/font"),
    WOFF("./static", "text/font");

    private final String fileRootPath;
    private final String contentType;

    AcceptType(String fileRootPath, String contentType) {
        this.fileRootPath = fileRootPath;
        this.contentType = contentType;
    }

    public static AcceptType of(String filePath) {
        String fileExtension = IOUtils.extractExtension(filePath);

        return Stream.of(values())
            .filter(type -> type.toString().equalsIgnoreCase(fileExtension))
            .findFirst()
            .orElse(HTML);
    }

    public String getFileRootPath() {
        return fileRootPath;
    }

    public String getContentType() {
        return contentType;
    }
}
