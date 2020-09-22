package web.server.domain.request;

import lombok.Getter;

@Getter
public class RequestUri {

    private static final String URI_DELIMITER_REGEX = "\\?";
    private static final String URI_DELIMITER = "?";

    private final String path;
    private final String query;

    public RequestUri(String requestUri) {
        String[] pathQuerySplit = requestUri.split(URI_DELIMITER_REGEX);
        this.path = pathQuerySplit[0];
        this.query = findQuery(requestUri);
    }

    private String findQuery(String requestUri) {
        String[] pathQuerySplit = requestUri.split(URI_DELIMITER_REGEX);
        String query = "";
        if (hasQueryString(pathQuerySplit.length)) {
            int delimiterIndex = requestUri.indexOf(URI_DELIMITER);
            query = requestUri.substring(delimiterIndex + 1);
        }
        return query;
    }

    private boolean hasQueryString(int pathQuerySplitSize) {
        return pathQuerySplitSize >= 2;
    }
}
