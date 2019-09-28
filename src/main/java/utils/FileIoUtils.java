package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class FileIoUtils {
    public static boolean isExistFile(String filePath) throws URISyntaxException {
        try {
            return Files.exists(Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI()));
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
        return Files.readAllBytes(path);
    }

    public static Optional<String> getExtension(String filePath) {
        String[] pathTokens = filePath.split("\\.");
        if (pathTokens.length > 1) {
            return Optional.of(pathTokens[pathTokens.length - 1]);
        }

        return Optional.empty();
    }
}
