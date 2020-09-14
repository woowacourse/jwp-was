package webserver.requestmapping;

import java.util.stream.Stream;

public enum FileExtensionType {
    HTML,
    CSS,
    JS,
    ICO,
    TTF,
    WOFF;

    public static boolean isSupportedFile(String fileName) {
        return Stream.of(values())
            .map(FileExtensionType::toExtensionFormat)
            .anyMatch(fileName::endsWith);
    }

    private static String toExtensionFormat(FileExtensionType fileExtension) {
        String lowercase = fileExtension.name().toLowerCase();
        return String.format(".%s", lowercase);
    }
}
