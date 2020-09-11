package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    private static final String BASE_PATH = "templates";
    public static final String NOT_FOUND = "404 NOT FOUND 잘 부탁드립니다.";
    public static final String INDEX_PAGE = "/index.html";

    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        try {
            if (filePath.equals("/")) {
                return Files.readAllBytes(
                    Paths.get(FileIoUtils.class.getClassLoader().getResource(BASE_PATH + INDEX_PAGE).toURI()));
            }
            Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(BASE_PATH + filePath).toURI());
            return Files.readAllBytes(path);
        } catch (NullPointerException e) {
            return NOT_FOUND.getBytes();
        }
    }
}
