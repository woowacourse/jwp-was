package webserver.http.response;

import java.util.Arrays;

public enum FileType {
    CSS("css"), JAVASCRIPT("js");

    private final String extensionName;

    FileType(String extensionName) {
        this.extensionName = extensionName;
    }

    public static boolean isSupportedFile(String target) {
        return Arrays.stream(FileType.values())
                .anyMatch(type -> target.equals(type.extensionName));
    }
}
