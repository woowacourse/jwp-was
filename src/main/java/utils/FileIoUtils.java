package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        String checkPath = "./static/" + filePath;
        try {
            Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(checkPath).toURI());
            return Files.readAllBytes(path);
        } catch (NullPointerException e) {
            checkPath = "./templates/" + filePath;
            Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(checkPath).toURI());
            return Files.readAllBytes(path);
        }
    }
}
