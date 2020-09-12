package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileIoUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileIoUtils.class);

    public static byte[] loadFileFromClasspath(String filePath) throws IOException {
        URL resource = FileIoUtils.class.getClassLoader()
                .getResource(filePath);
        try {
            if (resource != null) {
                Path path = Paths.get(resource.toURI());
                return Files.readAllBytes(path);
            }
            return null;
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
            throw new IllegalArgumentException(String.format(
                    "[%s] is not formatted strictly according to RFC2396 and cannot be converted to a URI",
                    filePath));
        }
    }
}

