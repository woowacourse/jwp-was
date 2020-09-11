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

    private static final String DEFAULT_DIRECTORY = "webapp/";

    public static byte[] loadFileFromClasspath(String filePath)
        throws IOException, URISyntaxException {
        Path path = Paths.get(findFileUri(filePath));
        return Files.readAllBytes(path);
    }

    private static URI findFileUri(String filePath) throws URISyntaxException {
        URL resource = FileIoUtils.class.getClassLoader().getResource(DEFAULT_DIRECTORY + filePath);
        if (Objects.isNull(resource)) {
            throw new FileNotExitsException(filePath);
        }
        return Objects.requireNonNull(resource).toURI();
    }
}
