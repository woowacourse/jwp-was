package http.request;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static java.net.URLDecoder.decode;

public class HttpUri {
    private static final int PATH_INDEX = 0;
    private static final String QUERY_STRING_DELIMITER = "\\?";

    private String path;

    HttpUri(String uri) throws UnsupportedEncodingException {
        String decodedUri = decode(uri, StandardCharsets.UTF_8.name());
        String[] splicedUri = decodedUri.split(QUERY_STRING_DELIMITER);

        path = splicedUri[PATH_INDEX];
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return path;
    }
}
