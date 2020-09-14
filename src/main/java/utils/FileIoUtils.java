package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    private static final String INDEX_PATH = "/";
    private static final String FILE_DIRECTORY = "./templates";

    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        if (INDEX_PATH.equals(filePath)) {
            return "Hello World".getBytes();
        }
        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(FILE_DIRECTORY + filePath).toURI());
        return Files.readAllBytes(path);
    }
}
