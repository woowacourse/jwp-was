package http.request;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static java.net.URLDecoder.decode;

public class HttpUri {
    private static final int PATH_INDEX = 0;
    private static final int QUERY_INDEX = 1;
    private static final int PATH_WITH_QUERY_PARAMS = 2;

    private static final String URI_DELIMITER = "\\?";

    private String path;
    private String queryParams;

    public HttpUri(String uri) throws UnsupportedEncodingException {
        String decodedUri = decode(uri, StandardCharsets.UTF_8.name());
        String[] splicedUri = decodedUri.split(URI_DELIMITER);

        path = splicedUri[PATH_INDEX];

        if (hasQueryParams(splicedUri)) {
            queryParams = splicedUri[QUERY_INDEX];
        }
    }

    private boolean hasQueryParams(String[] splicedUri) {
        return splicedUri.length == PATH_WITH_QUERY_PARAMS;
    }

    public String getPath() {
        return path;
    }

    public String getQueryParams() {
        return queryParams;
    }

    @Override
    public String toString() {
        return "HttpUri{" +
                "path='" + path + '\'' +
                ", queryParams=" + queryParams +
                '}';
    }
}
