package utils;

import webserver.resolver.NotFoundException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        URL resource = Optional.ofNullable(FileIoUtils.class.getClassLoader().getResource(filePath)).orElseThrow(NotFoundException::new);
        Path path = Paths.get(resource.toURI());
        return Optional.of(Files.readAllBytes(path)).orElseThrow(NotFoundException::new);
    }
}
