package utils;

import exceptions.NotFoundFileException;
import exceptions.NotFoundURIException;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
            Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
            return Files.readAllBytes(path);
    }

    public static String loadMIMEFromClasspath(String filePath) {
        return new Tika().detect(filePath);
    }
}
