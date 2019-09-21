package utils;

import http.HttpMediaType;

public class MediaTypeParser {
    private static final String STYLESHEET = "css";

    public static HttpMediaType parse(String path) {
        String fileName = path.substring(path.lastIndexOf('/') + 1);
        String[] splicedFileName = fileName.split("\\.");
        String extension = splicedFileName[splicedFileName.length - 1];
        return extension.equals(STYLESHEET)
                ? HttpMediaType.CSS
                : HttpMediaType.HTML;
    }
}
