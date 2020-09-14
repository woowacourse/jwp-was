package webserver;

import exception.FileNotReadableException;
import exception.InvalidUriException;
import utils.FileIoUtils;
import utils.StringUtils;

public class HttpUri {
    private static final String TEMPLATES_PATH = "./templates";

    private final String uri;

    public HttpUri(String uri) {
        StringUtils.validateNonNullAndNotEmpty(uri);
        validateFirstCharacterIsSlash(uri);

        this.uri = uri;
    }

    private void validateFirstCharacterIsSlash(String uri) {
        if (!uri.startsWith("/")) {
            throw new InvalidUriException(uri);
        }
    }

    public byte[] readFile() {
        String filePath = TEMPLATES_PATH + uri;
        try {
            return FileIoUtils.loadFileFromClasspath(filePath);
        } catch (Exception e) {
            throw new FileNotReadableException(filePath);
        }
    }
}
