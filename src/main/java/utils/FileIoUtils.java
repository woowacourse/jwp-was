package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileIoUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileIoUtils.class);

    public static byte[] loadFileFromClasspath(String filePath) throws IOException {
        try {
            Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
            return Files.readAllBytes(path);
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(String.format(
                    "[%s] is not formatted strictly according to RFC2396 and cannot be converted to a URI",
                    filePath));
        }
    }
}
