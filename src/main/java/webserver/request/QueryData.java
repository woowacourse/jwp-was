package webserver.request;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class QueryData {

    private static final String URI_USING_QUERY_STRING_REGIX = "/.*?.*=.*";
    private static final String QUERY_STRING_REGIX = "=.*";

    private Map<String, String> queryData;

    QueryData(String query) {
        this.queryData = convertQueryToMap(query);
    }

    private Map<String, String> convertQueryToMap(String query) {
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<>();

        for (String param : params) {
            String [] split = param.split("=");
            if (split.length != 2) {
                throw new IllegalArgumentException("input format is wrong.");
            }
            String name = split[0];
            String value = split[1];

            map.put(name, value);
        }
        return Collections.unmodifiableMap(map);
    }

    static boolean isUriUsingQuery(String uri) {
        return uri.matches(URI_USING_QUERY_STRING_REGIX);
    }

    Map<String, String> getQueryData() {
        return queryData;
    }
}
