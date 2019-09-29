package http.request;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static java.net.URLDecoder.decode;

public class HttpUri {
    private static final int PATH_INDEX = 0;
    private static final int QUERY_INDEX = 1;
    private static final int PATH_WITH_QUERY_PARAMS = 2;

    private static final char PATH_DELIMITER = '/';
    private static final String QUERY_STRING_DELIMITER = "\\?";
    private static final String DOT = ".";
    private static final String EMPTY = "";

    private String path;
    private String queryParams;

    public HttpUri(String uri) throws UnsupportedEncodingException {
        String decodedUri = decode(uri, StandardCharsets.UTF_8.name());
        String[] splicedUri = decodedUri.split(QUERY_STRING_DELIMITER);

        path = splicedUri[PATH_INDEX];
        queryParams = hasQueryParams(splicedUri) ? splicedUri[QUERY_INDEX] : EMPTY;
    }

    private boolean hasQueryParams(String[] queryString) {
        return queryString.length == PATH_WITH_QUERY_PARAMS;
    }

    public boolean hasExtension() {
        String fileName = path.substring(path.lastIndexOf(PATH_DELIMITER) + 1);
        return fileName.contains(DOT);
    }

    public String getPath() {
        return path;
    }

    public String getQueryParams() {
        return queryParams;
    }

    @Override
    public String toString() {
        return path + (queryParams.isEmpty() ? EMPTY : "?" + queryParams);
    }
}
