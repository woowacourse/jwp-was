package http;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static java.net.URLDecoder.decode;

public class HttpUrl {
    private static final int PATH_INDEX = 0;
    private static final int QUERY_INDEX = 1;
    private static final int PATH_WITH_QUERY_PARAMS = 2;

    private static final String URL_DELIMITER = "\\?";

    private String path;
    private QueryParams queryParams;

    public HttpUrl(String url) throws UnsupportedEncodingException {
        String decodedUrl = decode(url, StandardCharsets.UTF_8.name());
        String[] splicedUrl = decodedUrl.split(URL_DELIMITER);

        path = splicedUrl[PATH_INDEX];

        if (hasQueryParams(splicedUrl)) {
            queryParams = new QueryParams(splicedUrl[QUERY_INDEX]);
        }
    }

    private boolean hasQueryParams(String[] splicedUrl) {
        return splicedUrl.length == PATH_WITH_QUERY_PARAMS;
    }

    public String getPath() {
        return path;
    }

    QueryParams getQueryParams() {
        return queryParams;
    }

    @Override
    public String toString() {
        return "HttpUrl{" +
                "path='" + path + '\'' +
                ", queryParams=" + queryParams +
                '}';
    }
}
