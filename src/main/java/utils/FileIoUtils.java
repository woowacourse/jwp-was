package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    private static final String INDEX_PATH = "/";
    private static final String TEMPLATE_DIRECTORY = "./templates";
    private static final String STATIC_DIRECTORY = "./static";
    private static final String HTML_FILE = ".html";
    private static final String FAVICON = ".ico";

    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        if (INDEX_PATH.equals(filePath)) {
            return "Hello World".getBytes();
        }
        String originalFilePath = searchFile(filePath);
        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(originalFilePath).toURI());
        return Files.readAllBytes(path);
    }

    private static String searchFile(String filePath) {
        if (filePath.endsWith(HTML_FILE) || filePath.endsWith(FAVICON)) {
            return TEMPLATE_DIRECTORY + filePath;
        }
        return STATIC_DIRECTORY + filePath;
    }
}
