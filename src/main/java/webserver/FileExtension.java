package webserver;

import java.util.Arrays;

public enum FileExtension {
    HTML(".html", false),
    CSS(".css", true),
    JAVASCRIPT(".js", true),
    WOFF(".woff", true),
    TTF(".ttf", true);

    private final String extension;
    private final boolean staticDirectory;

    FileExtension(String extension, boolean staticDirectory) {
        this.extension = extension;
        this.staticDirectory = staticDirectory;
    }

    public static FileExtension find(String uri) {
        return Arrays.stream(FileExtension.values())
            .filter(fileExtension -> fileExtension.isSameExtension(uri))
            .findFirst()
            .orElseThrow(
                () -> new IllegalArgumentException(uri + "에 해당하는 FileExtension을 찾지 못했습니다!"));
    }

    public static boolean isFileExtension(String uri) {
        return Arrays.stream(FileExtension.values())
            .anyMatch(fileExtension -> fileExtension.isSameExtension(uri));
    }

    public boolean isStaticDirectory() {
        return this.staticDirectory;
    }

    private boolean isSameExtension(String uri) {
        return uri.endsWith(this.extension);
    }
}
