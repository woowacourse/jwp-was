package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {

    public static final String TEMPLATES_PREFIX = "templates";
    public static final String STATIC_PREFIX = "static";

    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        return Files.readAllBytes(getPathByExtension(filePath));
    }

    private static Path getPathByExtension(String filePath) throws URISyntaxException {
        if (filePath.endsWith("html")) {
            return Paths.get(FileIoUtils.class.getClassLoader().getResource(TEMPLATES_PREFIX + filePath).toURI());
        }
        return Paths.get(FileIoUtils.class.getClassLoader().getResource(STATIC_PREFIX + filePath).toURI());
    }
}

