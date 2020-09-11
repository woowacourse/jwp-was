package http;

import java.util.Arrays;

public enum StaticFiles {
    HTML(".html", "./templates", "text/html;charset=UTF-8"),
    CSS(".css", "./static", "text/css;charset=UTF-8"),
    JAVASCRIPT(".js", "./static", "application/javascript;charset=UTF-8");

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
            .orElseThrow(IllegalArgumentException::new)
            .getDirectory();
    }

    public static String getContentType(String inputStaticFileType) {
        return Arrays.stream(values())
            .filter(staticFile -> inputStaticFileType.endsWith(staticFile.staticFileExtension))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new)
            .contentType;
    }

    private String getDirectory() {
        return directory;
    }
}
