package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.exception.InvalidFileException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileIoUtils.class);

    public static byte[] loadFileFromClasspath(String filePath) {
        try {
            Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
            return Files.readAllBytes(path);
        } catch (Exception e) {
            logger.error("{}", e);
            throw new InvalidFileException();
        }
    }
}
