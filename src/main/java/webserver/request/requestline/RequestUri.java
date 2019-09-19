package webserver.request.requestline;

import webserver.request.QueryStringParser;

public class RequestUri {

    private static final String QUERY_STRING_DELIMITER = "\\?";
    private static final String EXTENSION_DELIMITER = "\\.";
    public static final String QUERY_STRING_MARK = "?";
    private String uri;

    public RequestUri(String uri) {
        this.uri = uri;
    }

    public String findUriPrefix() {
        RequestUriExtension extension = findExtension();
        return extension.getUriPrefix();
    }

    public RequestUriExtension findExtension() {
        if (hasQueryString()) {
            return RequestUriExtension.NONE;
        }

        String[] tokens = uri.split(EXTENSION_DELIMITER);
        int extensionIndex = tokens.length - 1;
        String uriExtension = tokens[extensionIndex];

        return RequestUriExtension.findExtension(uriExtension);
    }

    private boolean hasQueryString() {
        return uri.contains(QUERY_STRING_MARK);
    }

    public QueryParams findQueryParams() {
        if (hasQueryString()) {
            String queryString = uri.split(QUERY_STRING_DELIMITER)[1];
            return QueryStringParser.parseQueryParams(queryString);
        }

        return new QueryParams();
    }

    public String getUri() {
        return uri;
    }
}
