package utils;

import webserver.NotFoundException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        try {
            Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
            return Optional.of(Files.readAllBytes(path)).orElseThrow(NotFoundException::new);
        } catch (Exception e) {
            return null;
        }

    }
}
