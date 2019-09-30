package http;

import java.util.Arrays;

public enum MimeType {
    DEFAULT("text/plain"),
    HTML("text/html"),
    JS("application/javascript"),
    CSS("text/css");

    private static final String EXTENSION_DELIMITER = "\\.";

    private String mimeType;

    MimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public static String of(String filePath) {
        String[] extension = filePath.split(EXTENSION_DELIMITER);
        return Arrays.stream(MimeType.values())
                .filter(mimeType -> mimeType.name().toLowerCase().equals(extension[extension.length - 1]))
                .map(mimeType -> mimeType.mimeType)
                .findAny()
                .orElse(MimeType.DEFAULT.mimeType)
                ;
    }
}