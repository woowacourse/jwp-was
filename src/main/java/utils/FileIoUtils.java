package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class FileIoUtils {
    private static final List<String> prefixes = Arrays.asList("./templates", "./static");
    private static final Logger log = LoggerFactory.getLogger(FileIoUtils.class);

    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException, UrlNotFoundException {
        URL url = getUrl(filePath);
        if (url == null) {
            throw new UrlNotFoundException();
        }

        Path path = Paths.get(url.toURI());
        return Files.readAllBytes(path);
    }

    private static URL getUrl(String filePath) {
        URL url = null;
        for (String prefix : prefixes) {
            url = FileIoUtils.class.getClassLoader().getResource(prefix + filePath);
            if (url != null) {
                break;
            }
        }
        return url;
    }
}
