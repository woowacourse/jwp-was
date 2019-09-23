package http.request;

import java.util.Arrays;

public enum RequestContentType {

    JS(".js", "../resources/static", "application/javascript"),
    CSS(".css", "../resources/static", "text/css"),
    HTML(".html", "../resources/templates", "text/html"),
    ICO(".ico", "../resources/templates", "image/x-icon"),
    WOFF(".woff", "../resources/static", "font/woff"),
    TTF(".ttf", "../resources/static", "font/ttf"),
    NON_FILE_REQUEST("", "", "NOT_APPLICABLE");

    private String pattern;
    private String destinationPath;
    private String contentType;

    RequestContentType(String pattern, String destinationPath, String contentType) {
        this.pattern = pattern;
        this.destinationPath = destinationPath;
        this.contentType = contentType;
    }

    public static RequestContentType findType(String urlPath) {
        return Arrays.stream(values())
                .filter(type -> urlPath.contains(type.getExtension()))
                .findAny()
                .orElse(NON_FILE_REQUEST);
    }

    public String getExtension() {
        return pattern;
    }

    public String getDestinationPath() {
        return destinationPath;
    }

    public String getContentType() {
        return contentType;
    }
}
