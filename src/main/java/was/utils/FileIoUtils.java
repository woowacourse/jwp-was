package was.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) {
        try {
            Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
            return Files.readAllBytes(path);
        } catch (Exception e) {
            throw new FileLoadFailException(filePath);
        }
    }

    public static String getFileExtension(String fileName) {
        int beginIndex = fileName.lastIndexOf(".") + 1;
        return fileName.substring(beginIndex);
    }
}
