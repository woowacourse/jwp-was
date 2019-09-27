package utils.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.exception.NotExistPathException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FileIoUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileIoUtils.class);
    private static final String TEMPLATES_PACKAGE = "./templates";
    private static final Map<String, String> DEFAULT_PATH = new HashMap<>();
    static {
        DEFAULT_PATH.put("/index.html", TEMPLATES_PACKAGE);
        DEFAULT_PATH.put("/user/form.html", TEMPLATES_PACKAGE);
        DEFAULT_PATH.put("/user/login.html", TEMPLATES_PACKAGE);
    }

    public static Optional<String> loadFileFromClasspath(String filePath) {
        try {
            Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
            byte[] convertPath = Files.readAllBytes(path);
            return Optional.of(new String(convertPath));
        } catch (IOException | URISyntaxException | NullPointerException e) {
            logger.debug(e.getMessage());
            return Optional.empty();
        }
    }

    public static String convertPath(String path) {
        if (DEFAULT_PATH.containsKey(path)) {
            return DEFAULT_PATH.get(path) + path;
        }
        throw new NotExistPathException();
    }
}