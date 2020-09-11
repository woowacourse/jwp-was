package utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class FileIoUtils {

    public static byte[] loadFileFromClasspath(String filePath)
        throws IOException, URISyntaxException {
        Path path = getPath(filePath);
        return Files.readAllBytes(path);
    }

    private static Path getPath(String filePath) throws URISyntaxException {
        URL resource = FileIoUtils.class.getClassLoader().getResource(filePath);
        URI uri = Objects.requireNonNull(resource).toURI();
        return Paths.get(uri);
    }
}
