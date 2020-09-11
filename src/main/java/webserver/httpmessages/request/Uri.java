package webserver.httpmessages.request;

import java.util.HashMap;
import java.util.Map;

public class Uri {

    private static final String QUERY_STRING_REGULAR_EXPRESSION = "/.*?.*=.*";
    private static final String STATIC_RESOURCE_REQUEST_REGULAR_EXPRESSION = "/.*..*";

    private String uri;

    public Uri(String uri) {
        this.uri = uri;
    }

    public boolean isQueryString() {
        return uri.matches(QUERY_STRING_REGULAR_EXPRESSION);
    }

    public Map<String, String> getQueryData() {
        if (!isQueryString()) {
            throw new UnsupportedOperationException(
                "this function can be used only when uri is query string.");
        }
        Map<String, String> queryData = new HashMap<>();

        // Todo: 구현...

        return queryData;
    }

    public String getUri() {
        return uri;
    }
}
