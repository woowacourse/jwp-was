package http.request;

import java.util.Arrays;

public enum RequestUrlType {
//    private static final String STATIC_FILE_PATH = "../resources/static";
//    private static final String TEMPLATES = "../resources/templates";

    JS(".js", "../resources/static", "application/javascript"),
    CSS(".css", "../resources/static", "text/css"),
    HTML(".html", "../resources/templates", "text/html"),
    ICO(".ico", "../resources/templates", "image/x-icon"),
    WOFF(".woff", "../resources/static", "font/woff"),
    TTF(".ttf", "../resources/static", "font/ttf"),
    REDIRECTION("?", "../resources", "NOT_APPLICABLE");

    private String extension;
    private String folderPath;
    private String contentType;

    RequestUrlType(String extension, String folderPath, String contentType) {
        this.extension = extension;
        this.folderPath = folderPath;
        this.contentType = contentType;
    }

    public static RequestUrlType findType(String urlPath) {
        return Arrays.stream(values())
                .filter(type -> urlPath.contains(type.getExtension()))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getExtension() {
        return extension;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public String getContentType() {
        return contentType;
    }
}
