package webserver.httpmessages.request;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Uri {

    private static final String QUERY_STRING_REGULAR_EXPRESSION = "/.*?.*=.*";

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
        String[] pathAndQuery = uri.split("\\?");
        if (pathAndQuery.length > 2) {
            throw new IllegalArgumentException("The uri format is incorrect. (uri : " + uri + ")");
        }
    }

    private String findPathFromUri() {
        String[] pathAndQuery = uri.split("\\?");
        return pathAndQuery[0];
    }

    boolean isUsingQueryString() {
        return uri.matches(QUERY_STRING_REGULAR_EXPRESSION);
    }

    Map<String, String> getDataFromGetMethodUri() {
        if (!isUsingQueryString()) {
            throw new UnsupportedOperationException(
                "this function can be used only when uri is query string.");
        }
        String query = findQueryStringFromUri();
        Map<String, String> queryData = convertQueryToMap(query);

        return Collections.unmodifiableMap(queryData);
    }

    private String findQueryStringFromUri() {
        if (!isUsingQueryString()) {
            throw new IllegalArgumentException("The uri is not used Query String. (uri : " + uri + ")");
        }
        String[] pathAndQuery = uri.split("\\?");
        return pathAndQuery[1];
    }

    private Map<String, String> convertQueryToMap(String query) {
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<>();

        for (String param : params) {
            String [] p=param.split("=");
            String name = p[0];

            if (p.length>1) {
                String value = p[1];
                map.put(name, value);
            }
        }
        return map;
    }

    String getPath() {
        return path;
    }

    boolean isPath(String uriPath) {
        return this.path.equals(uriPath);
    }
}
