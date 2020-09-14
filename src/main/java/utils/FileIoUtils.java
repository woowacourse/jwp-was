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
    public static final String NOT_FOUND = "404 NOT FOUND 잘 부탁드립니다.";
    public static final String INDEX_PAGE = "/index.html";

    public static byte[] loadFileFromClasspath(String filePath) throws IOException {
        if (filePath.equals("/")) {
            return loadFileFromClasspath("/index.html");
        }

        if(!filePath.endsWith(".html")) {
            return "".getBytes();
        }

        Path path = BASE_PATH.stream()
            .map(base -> getPath(base + filePath))
            .filter(Objects::nonNull)
            .findAny()
            .orElseGet(() -> getPath("static/notFound.html"));

        return Files.readAllBytes(path);
    }

    private static Path getPath(String path) {
        try {
            return Paths.get(FileIoUtils.class.getClassLoader().getResource(path).toURI());
        } catch (NullPointerException e) {
            return null;
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException();
        }
    }
}
