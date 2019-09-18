package webserver.request.requestline;

import java.util.Arrays;

public class RequestUri {

    private static final String QUERY_STRING_MARK = "\\?";
    private static final String EXTENSION_DELIMITER = "\\.";
    private static final String QUERY_STRING_DELIMITER = "&";
    private static final String PARAM_DELIMITER = "=";
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
        QueryParams queryParams = new QueryParams();

        if (hasQueryString()) {
            String queryString = uri.split(QUERY_STRING_MARK)[1];
            String[] queryTokens = queryString.split(QUERY_STRING_DELIMITER);

            Arrays.stream(queryTokens).forEach(queryToken -> {
                String[] paramTokens = queryToken.split(PARAM_DELIMITER);
                String key = paramTokens[0];
                String value = paramTokens[1];

                queryParams.addParam(key, value);
            });
        }

        return queryParams;
    }

    public String getUri() {
        return uri;
    }
}
