package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import kr.wootecat.dongle.http.exception.ResourceNotFoundException;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        final URL resource = FileIoUtils.class.getClassLoader().getResource(filePath);
        if (resource == null) {
            throw new ResourceNotFoundException(filePath);
        }
        Path path = Paths.get(resource.toURI());
        return Files.readAllBytes(path);
    }
}
