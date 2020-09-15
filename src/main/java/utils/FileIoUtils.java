package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    private final static Logger logger = LoggerFactory.getLogger(FileIoUtils.class);

    public static byte[] loadFileFromClasspath(String filePath) throws IOException {
        Path path;
        try {
            path = Paths.get(FileIoUtils.class.getClassLoader().getResource(appendBasePath(filePath)).toURI());
        } catch (URISyntaxException | NullPointerException e) {
            logger.error("Illegal file path");
            throw new IllegalArgumentException("Illegal file path");
        }
        return Files.readAllBytes(path);
    }

    private static String appendBasePath(String path) {
        String baseUrl = "./static";
        if (path.contains(".html") || path.contains("favicon.ico")) {
            baseUrl = "./templates";
        }
        return baseUrl + path;
    }
}
