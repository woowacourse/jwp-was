package utils.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public final class FileIoUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileIoUtils.class);

    public static Optional<String> loadFileFromClasspath(String filePath) {
        try {
            return Optional.of(
                    new String(
                        Files.readAllBytes(
                                Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI())
                        )
                    )
            );
        } catch (IOException | URISyntaxException | NullPointerException e) {
            logger.error(e.getMessage());
            return Optional.empty();
        }
    }

    private FileIoUtils() {}
}