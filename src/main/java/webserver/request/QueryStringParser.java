package webserver.request;

import webserver.request.requestline.QueryParams;

import java.util.Arrays;

public class QueryStringParser {

    private static final String QUERY_STRING_DELIMITER = "&";
    private static final String PARAM_DELIMITER = "=";

    public static QueryParams parseQueryParams(String queryString) {
        QueryParams queryParams = new QueryParams();
        String[] queryTokens = queryString.split(QUERY_STRING_DELIMITER);

        Arrays.stream(queryTokens).forEach(queryToken -> {
            String[] paramTokens = queryToken.split(PARAM_DELIMITER);
            String key = paramTokens[0];
            String value = paramTokens[1];

            queryParams.addParam(key, value);
        });

        return queryParams;
    }
}
