package http;

import java.util.HashMap;
import java.util.Map;

public class HttpUriParser {

    public static HttpUri parse(String uri) {
        boolean hasQueryString = uri.contains("?");

        if (!hasQueryString) {
            return new HttpUri(uri, false, new HashMap<>());
        }

        String[] uriTokens = uri.split("\\?");
        Map<String, String> queryParams = QueryParamsParser.parse(uriTokens[1]);
        return new HttpUri(uri, true, queryParams);
    }
}
