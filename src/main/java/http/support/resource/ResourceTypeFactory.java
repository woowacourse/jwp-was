package http.support.resource;

import http.exception.NoSuchExtensionException;

import java.util.Arrays;
import java.util.List;

public class ResourceTypeFactory {
    private static final List<String> TEXT_EXTENSIONS = Arrays.asList("html", "ico", "css", "js");
    private static final List<String> IMAGE_EXTENSIONS = Arrays.asList("jpeg", "jpg");
    private static final List<String> FONT_EXTENSIONS = Arrays.asList("ttf", "woff");

    public static ResourceType getInstance(String path) {
        String extension = path.substring(path.lastIndexOf(".") + 1);

        if (TEXT_EXTENSIONS.contains(extension)) {
            return new TextResourceType(extension);
        }

        if (IMAGE_EXTENSIONS.contains(extension)) {
            return new ImageResourceType(extension);
        }

        if (FONT_EXTENSIONS.contains(extension)) {
            return new FontResourceType(extension);
        }

        throw new NoSuchExtensionException("지원하는 확장자가 없습니다");
    }
}
