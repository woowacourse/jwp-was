package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(checkHomePath(filePath)).toURI());
        return Files.readAllBytes(path);
    }

    private static String checkHomePath(String filePath) {
        return filePath.equals("../resources/templates/") ? filePath + "index.html" : filePath;
    }
}
