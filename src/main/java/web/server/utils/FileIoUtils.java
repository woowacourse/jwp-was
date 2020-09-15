package web.server.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {

    public static byte[] loadFileFromClasspath(String filePath) {
        try {
            Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
            return Files.readAllBytes(path);
        } catch (IOException | URISyntaxException e) {
            throw new IllegalArgumentException("파일을 읽는 도중에 문제가 발생하였습니다.");
        }
    }

    public static byte[] loadFileFromRequest(StaticFileType staticFileType, String path) {
        String filePath = staticFileType.getPath() + path;
        return loadFileFromClasspath(filePath);
    }
}
