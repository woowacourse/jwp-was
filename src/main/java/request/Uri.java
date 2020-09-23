package request;

import java.util.Collections;
import java.util.Map;

class Uri {

    private static final String PATH_AND_QUERY_SEPARATOR = "\\?";

    private String uri;
    private String path;

    Uri(String uri) {
        validateUri(uri);
        this.uri = uri;
        this.path = findPathFromUri();
    }

    private void validateUri(String uri) {
        if (!uri.startsWith("/")) {
            throw new IllegalArgumentException("The uri format is incorrect. (uri : " + uri + ")");
        }
        String[] pathAndQuery = uri.split(PATH_AND_QUERY_SEPARATOR);
        final int pathAndQueryMaxLength = 2;

        if (pathAndQuery.length > pathAndQueryMaxLength) {
            throw new IllegalArgumentException("The uri format is incorrect. (uri : " + uri + ")");
        }
    }

    private String findPathFromUri() {
        String[] pathAndQuery = uri.split(PATH_AND_QUERY_SEPARATOR);
        return pathAndQuery[0];
    }

    public boolean isUsingQueryString() {
        return QueryData.isUriUsingQuery(uri);
    }

    Map<String, String> getDataFromGetMethodUri() {
        if (!isUsingQueryString()) {
            throw new UnsupportedOperationException(
                "this function can be used only when uri is query string.");
        }
        String query = findQueryStringFromUri();
        return Collections.unmodifiableMap(new QueryData(query).getQueryData());
    }

    private String findQueryStringFromUri() {
        if (!isUsingQueryString()) {
            throw new IllegalArgumentException(
                "The uri is not used QueryData String. (uri : " + uri + ")");
        }
        String[] pathAndQuery = uri.split(PATH_AND_QUERY_SEPARATOR);
        return pathAndQuery[1];
    }

    public String getPath() {
        return path;
    }

    boolean isPath(String uriPath) {
        return this.path.equals(uriPath);
    }

    @Override
    public String toString() {
        return uri;
    }
}
