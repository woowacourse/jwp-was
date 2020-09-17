package webserver.http.request;

import java.util.Arrays;

import webserver.http.exception.ResourceNotFoundException;

public enum StaticFiles {
    HTML(".html", "./templates", "text/html"),
    CSS(".css", "./static", "text/css"),
    JAVASCRIPT(".js", "./static", "application/javascript");

    private final String staticFileExtension;
    private final String directory;
    private final String contentType;

    StaticFiles(String staticFileExtension, String directory, String contentType) {
        this.staticFileExtension = staticFileExtension;
        this.directory = directory;
        this.contentType = contentType;
    }

    public static boolean endsWith(String inputStaticFileType) {
        return Arrays.stream(values())
            .anyMatch(staticFile -> inputStaticFileType.endsWith(staticFile.staticFileExtension));
    }

    public static String getDirectoryEndsWith(String path) {
        return Arrays.stream(values())
            .filter(staticFile -> path.endsWith(staticFile.staticFileExtension))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("해당 path의 파일이 있는 경로를 찾을 수 없습니다. " + path))
            .getDirectory();
    }

    public static String getContentType(String inputStaticFileType) {
        return Arrays.stream(values())
            .filter(staticFile -> inputStaticFileType.endsWith(staticFile.staticFileExtension))
            .findFirst()
            .orElseThrow(
                () -> new ResourceNotFoundException("해당 path의 파일의 ContentType을 찾을 수 없습니다. " + inputStaticFileType))
            .contentType;
    }

    private String getDirectory() {
        return directory;
    }
}
