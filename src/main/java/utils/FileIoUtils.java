package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    public static boolean exists(String filePath) {
        try {
            Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
            return Files.exists(path);
        } catch (NullPointerException | URISyntaxException e) {
            return false;
        }
    }

    public static byte[] loadFileFromClasspath(String filePath) {
        try {
            Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
            return Files.readAllBytes(path);
        } catch (IOException | URISyntaxException e) {
            throw new IllegalArgumentException("파일을 읽을 수 없습니다.");
        }
    }
}
