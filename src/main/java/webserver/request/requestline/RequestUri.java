package webserver.request.requestline;

public class RequestUri {

    private static final String QUERY_STRING_MARK = "\\?";
    private static final String EXTENSION_DELIMITER = "\\.";
    private String uri;

    public RequestUri(String uri) {
        this.uri = uri;
    }

    public String findUriPrefix() {
        RequestUriExtension extension = findExtension();
        return extension.getUriPrefix();
    }

    private RequestUriExtension findExtension() {
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

    public String getUri() {
        return uri;
    }
}
