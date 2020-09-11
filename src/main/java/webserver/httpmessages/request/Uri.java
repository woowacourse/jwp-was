package webserver.httpmessages.request;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Uri {

    private static final String QUERY_STRING_REGULAR_EXPRESSION = "/.*?.*=.*";

    private String uri;

    public Uri(String uri) {
        this.uri = uri;
    }

    public boolean isQueryString() {
        return uri.matches(QUERY_STRING_REGULAR_EXPRESSION);
    }

    public Map<String, String> getDataFromGetMethodUri() {
        if (!isQueryString()) {
            throw new UnsupportedOperationException(
                "this function can be used only when uri is query string.");
        }
        String query = findQueryStringFromUri(uri);
        Map<String, String> queryData = convertQueryToMap(query);

        return Collections.unmodifiableMap(queryData);
    }

    private String findQueryStringFromUri(String uri) {
        String[] pathAndQuery = uri.split("\\?");
        if (pathAndQuery.length != 2) {
            throw new IllegalArgumentException("The uri format is incorrect. (uri : " + uri + ")");
        }
        System.out.println("*** query string");
        System.out.println(pathAndQuery[1]);
        return pathAndQuery[1];
    }

    private Map<String, String> convertQueryToMap(String query) {
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<>();
        for (String param : params) {
            String [] p=param.split("=");
            String name = p[0];

            if(p.length>1) {
                String value = p[1];
                map.put(name, value);
            }
        }
        return map;
    }

    public String getUri() {
        return uri;
    }
}
