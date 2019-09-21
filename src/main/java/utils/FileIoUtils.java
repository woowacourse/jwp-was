package utils;

import http.exceptions.NoSuchResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtils.class);


    public static byte[] loadFileFromClasspath(String filePath) {
        try {
            Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
            return Files.readAllBytes(path);

        } catch (IOException | NullPointerException | URISyntaxException e) {
            log.error(e.getMessage());
            throw new NoSuchResource(e.getMessage());
        }
    }

    public static byte[] loadFileByPath(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new NoSuchResource();
        }
    }
}
