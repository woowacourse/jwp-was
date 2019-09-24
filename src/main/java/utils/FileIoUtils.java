package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class FileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        URL url = getUrlFromFilePath(filePath).orElseThrow(() -> LoadingFileFailException.fromFilePath(filePath));
        Path path = Paths.get(url.toURI());

        return Files.readAllBytes(path);
    }


    public static boolean canUseResourceFromFilePath(String filePath) {
        return getUrlFromFilePath(filePath)
                .map(url -> true)
                .orElse(false);
    }

    private static Optional<URL> getUrlFromFilePath(String filePath) {
        return Optional.ofNullable(FileIoUtils.class.getClassLoader().getResource(filePath));
    }
}
