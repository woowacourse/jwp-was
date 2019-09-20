package http;

import java.util.Map;

public class HttpUri {

    private String uri;
    private boolean hasQueryString;
    private Map<String, String> queryParams;

    public HttpUri(final String uri) {
        this.uri = uri;
        this.hasQueryString = uri.contains("?");
        if(hasQueryString) {
            String queryString = uri.split("\\?")[1];
            queryParams = QueryParamsParser.parse(queryString);
        }
    }
}
