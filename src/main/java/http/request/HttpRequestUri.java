package http.request;

import http.QueryString;
import http.exception.EmptyUriException;

public class HttpRequestUri {
    private static final String QUERY_STRING_DELIMITER = "?";
    private static final String FILE_EXTENSION_DELIMITER = ".";

    private final String uri;

    public HttpRequestUri(String uri) {
        if ("".equals(uri) || uri == null) {
            throw new EmptyUriException();
        }

        this.uri = uri;
    }

    public boolean addQueryString(QueryString queryString) {
        if (!uri.contains(QUERY_STRING_DELIMITER)) {
            return false;
        }

        String[] uriTokens = uri.split("\\" + QUERY_STRING_DELIMITER);
        queryString.add(uriTokens[1]);
        return true;
    }

    public boolean isFileUri() {
        return uri.contains(FILE_EXTENSION_DELIMITER);
    }

    @Override
    public String toString() {
        return uri;
    }
}
