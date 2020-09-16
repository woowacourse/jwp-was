package webserver.requestmapping;

import java.util.stream.Stream;

import exception.FileNotSupportedException;
import http.ContentType;

public enum FileExtensionType {
    HTML("./templates", ContentType.HTML),
    CSS("./static", ContentType.CSS),
    JS("./static", ContentType.JS),
    ICO("./templates", ContentType.ICO),
    TTF("./static", ContentType.TTF),
    WOFF("./static", ContentType.WWOF),
    WWF2("./static", ContentType.WWOF2);

    private String parentDirectory;
    private ContentType contentType;

    FileExtensionType(String parentDirectory, ContentType contentType) {
        this.parentDirectory = parentDirectory;
        this.contentType = contentType;
    }

    public static boolean isSupportedFile(String fileName) {
        return Stream.of(values())
            .map(FileExtensionType::toExtensionFormat)
            .anyMatch(fileName::endsWith);
    }

    private static String toExtensionFormat(FileExtensionType fileExtension) {
        String lowercase = fileExtension.name().toLowerCase();
        return String.format(".%s", lowercase);
    }

    public static String findLocalPath(String path) {
        return Stream.of(values())
            .filter(type -> path.endsWith(type.name().toLowerCase()))
            .map(type -> type.parentDirectory + path)
            .findFirst()
            .orElseThrow(FileNotSupportedException::new);
    }

    public static ContentType findMatchingContentType(String path) {
        return Stream.of(values())
            .filter(type -> path.endsWith(type.name().toLowerCase()))
            .map(type -> type.contentType)
            .findFirst()
            .orElse(ContentType.PLAIN);
    }
}
