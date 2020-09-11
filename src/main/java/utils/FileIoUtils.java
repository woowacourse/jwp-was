package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) throws IOException {
        Path path;
        try {
            path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
        } catch (URISyntaxException e) {
            return null;
        }
        return Files.readAllBytes(path);
    }
}
