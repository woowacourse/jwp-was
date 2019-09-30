package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        URL url = Optional.ofNullable(FileIoUtils.class.getClassLoader().getResource(filePath))
                .orElseThrow(FileNotFoundException::new);
        Path path = Paths.get(url.toURI());
        return Files.readAllBytes(path);
    }
}
