package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import javax.print.DocFlavor.STRING;

public class FileIoUtils {

    private static final String HTTP_HEADER_DELIMITER = " ";
    private static final int RESOURCE_PATH_INDEX = 1;
    private static final String ROOT_PATH = "/";
    private static final String INDEX_PATH = "/index";
    private static final String INDEX_SUFFIX = ".html";

    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
        return Files.readAllBytes(path);
    }

    public static String extractResourcePath(String headerLine) {
        String[] headerLineSegment = headerLine.split(HTTP_HEADER_DELIMITER);
        String resourcePath = headerLineSegment[RESOURCE_PATH_INDEX];

        if (ROOT_PATH.equals(resourcePath)) {
            return INDEX_PATH + INDEX_SUFFIX;
        }
        return resourcePath;
    }
}
