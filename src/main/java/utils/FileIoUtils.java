package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(getResourcePath(filePath)).toURI());
        return Files.readAllBytes(path);
    }

    private static String getResourcePath(String path) {
        if (path.endsWith(".html")) {
            return "./templates" + path;
        }
        return "./static" + path;
    }
}
