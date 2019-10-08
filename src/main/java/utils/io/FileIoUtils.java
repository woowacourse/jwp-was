package utils.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public final class FileIoUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileIoUtils.class);

    private static final String RESOURCES_FOLDER_PATH = "src/main/resources";

    public static Optional<String> loadFileFromClasspath(String filePath) {
        try {
            return Optional.of(
                    new String(
                            Files.readAllBytes(
                                    Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI())
                            )
                    )
            );
        } catch (IOException | URISyntaxException | NullPointerException e) {
            logger.error(e.getMessage());
            return Optional.empty();
        }
    }

    public static void saveFileAtClasspath(String filePath, String content) {
        try {
            final List<String> dirs = new ArrayList<>(Arrays.asList(RESOURCES_FOLDER_PATH.split("\\s*/\\s*")));
            if (dirs.get(0).trim().isEmpty()) {
                dirs.remove(0);
            }
            if (dirs.isEmpty()) {
                Files.write(Paths.get(filePath), content.getBytes());
                return;
            }
            final String head = dirs.remove(0);
            dirs.add(filePath);
            String[] tail = new String[dirs.size()];
            tail = dirs.toArray(tail);
            Files.write(Paths.get(head, tail), content.getBytes());
        } catch (IOException | NullPointerException e) {
            logger.error(e.getMessage());
        }
    }

    private FileIoUtils() {}
}