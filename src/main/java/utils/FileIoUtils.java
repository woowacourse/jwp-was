package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {

    private static final String DELIMITER = " ";
    private static final String TEMPLATE_PATH = "./templates";

    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
        return Files.readAllBytes(path);
    }

    public static byte[] loadFileFromRequest(String line) throws IOException, URISyntaxException {
        String path = line.split(DELIMITER)[1];
        return loadFileFromClasspath(TEMPLATE_PATH + path);
    }
}
