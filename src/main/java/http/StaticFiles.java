package http;

import java.util.Arrays;

public enum StaticFiles {
    HTML(".html", "./templates"),
    CSS(".css", "./static/css"),
    JS(".js", "./static/js");

    private final String StaticFileExtension;
    private final String directory;

    StaticFiles(String staticFileExtension, String directory) {
        StaticFileExtension = staticFileExtension;
        this.directory = directory;
    }

    public static String getDirectoryEndsWith(String path) {
        return Arrays.stream(values())
            .filter(staticFile -> path.endsWith(staticFile.StaticFileExtension))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new)
            .getDirectory();
    }

    public static boolean endsWith(String inputStaticFileType) {
        return Arrays.stream(values())
            .anyMatch(staticFile -> inputStaticFileType.endsWith(staticFile.StaticFileExtension));
    }

    public String getDirectory() {
        return directory;
    }
}
