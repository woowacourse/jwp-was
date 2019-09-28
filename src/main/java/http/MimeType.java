package http;

import utils.FileIoUtils;

import java.util.Arrays;

public enum MimeType {
    DEFAULT("text/plain"),
    HTML("text/html"),
    JS("application/javascript"),
    CSS("text/css");

    private String mimeType;

    MimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public static String of(String filePath) {
        String extension = FileIoUtils.getExtension(filePath).orElse("default");
        return Arrays.stream(MimeType.values())
                .filter(mimeType -> mimeType.name().equals(extension.toUpperCase()))
                .map(mimeType -> mimeType.mimeType)
                .findAny()
                .orElse(MimeType.DEFAULT.mimeType)
                ;
    }
}
