package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class FileIoUtils {

    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        Path path = Paths.get(Optional.ofNullable(FileIoUtils.class.getClassLoader().getResource(filePath))
            .orElse(new URL("/"))
            .toURI()
        );
        return Files.readAllBytes(path);
    }
}
