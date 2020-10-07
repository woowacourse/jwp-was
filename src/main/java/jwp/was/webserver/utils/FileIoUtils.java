package jwp.was.webserver.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class FileIoUtils {

    private static final String WEBAPP = "webapp";
    private static final String STATIC = "static";

    private FileIoUtils() {
    }

    public static byte[] loadFileFromClasspath(String filePath)
        throws IOException, URISyntaxException {
        String directory = WEBAPP;
        if (filePath.contains("/css/") || filePath.contains("/js/") || filePath.contains("/fonts/")
            || filePath.contains("/images/")) {
            directory = STATIC;
        }
        Path path = Paths.get(findFileUri(filePath, directory));
        return Files.readAllBytes(path);
    }

    private static URI findFileUri(String filePath, String directory) throws URISyntaxException {
        String fullFilePath = directory + filePath;
        URL resource = FileIoUtils.class.getClassLoader().getResource(fullFilePath);
        if (Objects.isNull(resource)) {
            throw new FileNotExitsException(filePath);
        }
        return Objects.requireNonNull(resource).toURI();
    }
}
