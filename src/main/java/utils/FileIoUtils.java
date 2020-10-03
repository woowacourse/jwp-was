package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileIoUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileIoUtils.class);

    public static byte[] loadFileFromClasspath(String filePath) {
        try {
            Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
            return Files.readAllBytes(path);
        } catch (URISyntaxException e) {
            logger.error(String.format("잘못된 형식의 URI입니다. : {'Error Message' : %s}", e.getMessage()));
            throw new FileCannotReadException(e.getMessage());
        } catch (IOException e) {
            logger.error(String.format("파일을 읽을 수 없습니다. : {'Error Message' : %s}", e.getMessage()));
            throw new FileCannotReadException(e.getMessage());
        }
    }
}
