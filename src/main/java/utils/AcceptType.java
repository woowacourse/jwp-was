package utils;

import java.util.stream.Stream;

public enum AcceptType {
    HTML("./templates"),
    CSS("./static"),
    JS("./static");

    private final String fileRootPath;

    AcceptType(String fileRootPath) {
        this.fileRootPath = fileRootPath;
    }

    public static AcceptType of(String filePath) {
        return Stream.of(values())
            .filter(type -> type.toString().equalsIgnoreCase(filePath))
            .findFirst()
            .orElse(HTML);
    }

    public String getFileRootPath() {
        return fileRootPath;
    }
}
