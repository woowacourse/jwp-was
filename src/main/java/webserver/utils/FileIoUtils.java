package webserver.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        URL resource = FileIoUtils.class.getClassLoader().getResource(filePath);
        if (Objects.isNull(resource)) {
            throw new IllegalArgumentException("파일이 없습니다.");
        }
        Path path = Paths.get(resource.toURI());
        return Files.readAllBytes(path);
    }
}
