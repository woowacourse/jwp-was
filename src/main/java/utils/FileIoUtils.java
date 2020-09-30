package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class FileIoUtils {

    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
        return Files.readAllBytes(path);
    }

    public static String encode(byte[] value) {
        return Base64.getEncoder().encodeToString(value);
    }

    public static byte[] decode(String value) {
        return Base64.getDecoder().decode(value);
    }
}
