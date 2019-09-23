package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
        return Files.readAllBytes(path);
    }

    public static boolean existFileInClasspath(String filePath) throws URISyntaxException {
        try {
            Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }
}
