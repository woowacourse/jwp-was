package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileIoUtils {
    private static final List<String> BASE_PATH = Arrays.asList("templates", "static");
    public static final String INDEX_PAGE = "/index.html";
    private static final String DEFAULT_PATH = "/";
    private static final String EXTENSION_DELIMITER = ".";
    private static final String NOT_FOUND_PAGE = "static/notFound.html";
    private static final String SUFFIX = ".html";

    public static byte[] loadFileFromClasspath(String filePath) throws IOException {
        if (filePath.equals(DEFAULT_PATH)) {
            return loadFileFromClasspath(INDEX_PAGE);
        }

        if (!filePath.contains(EXTENSION_DELIMITER)) {
            return loadFileFromClasspath("/" + filePath + SUFFIX);
        }

        Path path = BASE_PATH.stream()
            .map(base -> getPath(base + filePath))
            .filter(Objects::nonNull)
            .findAny()
            .orElseGet(() -> getPath(NOT_FOUND_PAGE));

        assert path != null;
        return Files.readAllBytes(path);
    }

    private static Path getPath(String path) {
        try {
            return Paths.get(Objects.requireNonNull(FileIoUtils.class.getClassLoader().getResource(path)).toURI());
        } catch (NullPointerException e) {
            return null;
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException();
        }
    }
}
