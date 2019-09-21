package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static constant.PathConstants.ERROR_PAGE_PATH;

public class FileIoUtils {

    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        URL resource = getResource(filePath);
        if (resource == null) {
            resource = getResource(ERROR_PAGE_PATH);
        }

        Path path = Paths.get(resource.toURI());
        return Files.readAllBytes(path);
    }

    private static URL getResource(final String filePath) {
        return FileIoUtils.class.getClassLoader().getResource(filePath);
    }
}
