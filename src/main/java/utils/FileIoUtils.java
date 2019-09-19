package utils;

import exceptions.NotFoundException;
import org.apache.tika.Tika;
import webserver.response.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        try {
            Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
            return Files.readAllBytes(path);
        } catch (NullPointerException e) {
            throw new NotFoundException("없는 경로입니다.", HttpStatus.NOT_FOUND);
        }
    }

    public static String loadMIMEFromClasspath(String filePath) {
        return new Tika().detect(filePath);
    }
}
