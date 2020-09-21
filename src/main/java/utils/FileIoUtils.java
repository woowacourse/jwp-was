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

    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        Path path;
        try {
            path = Paths.get(FileIoUtils.class.getClassLoader().getResource(appendBasePath(filePath)).toURI());
            return Files.readAllBytes(path);
        } catch (NullPointerException e) {
            logger.debug("Illegal file path so return empty");
            return new byte[0];
        }
    }

    private static String appendBasePath(String path) {
        String baseUrl = "./static";
        if (path.contains(".html") || path.contains("favicon.ico")) {
            baseUrl = "./templates";
        }
        return baseUrl + path;
    }
}
