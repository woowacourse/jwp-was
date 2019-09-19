package utils.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class FileIoUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileIoUtils.class);

    public static Optional<byte[]> loadFileFromClasspath(String filePath) {
        logger.debug("Route to: {}", filePath);
        try {
            return Optional.of(
                    Files.readAllBytes(
                            Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI())
                    )
            );
        } catch (IOException | URISyntaxException | NullPointerException e) {
            logger.debug(e.getMessage());
            return Optional.empty();
        }
    }
}