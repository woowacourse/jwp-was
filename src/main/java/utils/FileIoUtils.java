package utils;

import exceptions.NotFoundURIException;
import org.apache.tika.Tika;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) throws IOException {
        try {
            Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
            return Files.readAllBytes(path);
        } catch (URISyntaxException e) {
            throw new NotFoundURIException(filePath);
        }
    }

    public static String loadMIMEFromClasspath(String filePath) {
        return new Tika().detect(filePath);
    }
}
