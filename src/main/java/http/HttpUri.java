package http;

import http.exception.EmptyUriException;

public class HttpUri {
    private static final String FILE_EXTENSION_DELIMITER = ".";

    private final String uri;

    public HttpUri(String uri) {
        if ("".equals(uri) || uri == null) {
            throw new EmptyUriException();
        }

        this.uri = uri;
    }

    public boolean isFileUri() {
        return uri.contains(FILE_EXTENSION_DELIMITER);
    }

    @Override
    public String toString() {
        return uri;
    }
}
