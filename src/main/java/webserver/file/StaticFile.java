package webserver.file;

import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class StaticFile extends File {
    private static final String STATIC_PATH = "./static";

    public StaticFile(final String path) throws IOException, URISyntaxException {
        super(STATIC_PATH, path);
    }

    public static boolean supports(final String path) {
        try {
            return FileIoUtils.exists(makeFilePath(STATIC_PATH, path));
        } catch (URISyntaxException e) {
            return false;
        }
    }
}
