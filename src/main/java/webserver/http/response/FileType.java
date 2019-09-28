package webserver.http.response;

import exceptions.NotSupportedFileType;

import java.util.Arrays;

public enum FileType {
    ALL("*", "*/*"), CSS("css", "text/css"), JAVASCRIPT("js", "application/javascript"
    ), HTML("html", "text/html"), WOFF("woff", "application/font-woff"), TTF("ttf", "application/x-font-ttf"), ICO("ico", "image/x-icon");

    private final String extensionName;
    private final String mimeName;

    FileType(String extensionName, String mimeName) {
        this.extensionName = extensionName;
        this.mimeName = mimeName;
    }

    public static boolean isSupportedFile(String target) {
        return Arrays.stream(FileType.values())
                .anyMatch(type -> target.equals(type.extensionName));
    }

    public static FileType getTypeByExtension(String target) {
        return Arrays.stream(FileType.values())
                .filter(type -> target.equals(type.getExtensionName()))
                .findFirst().orElseThrow(() -> new NotSupportedFileType(target));
    }

    public String getExtensionName() {
        return extensionName;
    }

    public String getMimeName() {
        return mimeName;
    }
}
