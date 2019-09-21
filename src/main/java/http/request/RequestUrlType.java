package http.request;

import java.util.Arrays;

public enum RequestUrlType {
//    private static final String STATIC_FILE_PATH = "../resources/static";
//    private static final String TEMPLATES = "../resources/templates";

    JS(".js", "../resources/static"),
    CSS(".css", "../resources/static"),
    HTML(".html", "../resources/templates"),
    REDIRECTION("?", "../resources");

    private String extension;
    private String folderPath;

    RequestUrlType(String extension, String folderPath) {
        this.extension = extension;
        this.folderPath = folderPath;
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
}
