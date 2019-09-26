package webserver.http.response;

import java.util.Arrays;

public enum FileType {
    CSS("css", "text/css"), JAVASCRIPT("js", "application/javascript"
    ), HTML("html", "text/html");

    private final String extensionName;
    private final String memeName;

    FileType(String extensionName, String memeName) {
        this.extensionName = extensionName;
        this.memeName = memeName;
    }

    public static boolean isSupportedFile(String target) {
        return Arrays.stream(FileType.values())
                .anyMatch(type -> target.equals(type.extensionName));
    }

    public static FileType getTypeByExtension(String target) {
        return Arrays.stream(FileType.values())
                .filter(type -> target.equals(type.getExtensionName()))
                .findFirst().get();
    }

    public String getExtensionName() {
        return extensionName;
    }

    public String getMemeName() {
        return memeName;
    }

}
