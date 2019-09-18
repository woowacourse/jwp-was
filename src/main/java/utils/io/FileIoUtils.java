package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class FileIoUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileIoUtils.class);

    public static Optional<String> loadFileFromClasspath(String filePath) {
        try {
            final Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
            logger.debug(path.toString());
            return Optional.of(new String(Files.readAllBytes(path)));
        } catch (IOException | URISyntaxException | NullPointerException e) {
            logger.debug(e.getMessage());
            return Optional.empty();
        }
    }
}