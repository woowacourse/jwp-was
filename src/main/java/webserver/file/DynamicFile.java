package webserver.file;

import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class DynamicFile extends File {
    private static final String TEMPLATES_PATH = "./templates";

    public DynamicFile(final String path) throws IOException, URISyntaxException {
        super(TEMPLATES_PATH, path);
    }

    public static boolean supports(final String path) {
        try {
            return FileIoUtils.exists(makeFilePath(TEMPLATES_PATH, path));
        } catch (URISyntaxException e) {
            return false;
        }
    }
}
