package webserver.request;

import exception.ParamDecodeException;
import webserver.request.requestline.QueryParams;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;

public class QueryStringParser {

    private static final String QUERY_STRING_DELIMITER = "&";
    private static final String PARAM_DELIMITER = "=";

    private QueryStringParser() {
    }

    public static QueryParams parseQueryParams(String queryString) {
        QueryParams queryParams = new QueryParams();
        String[] queryTokens = queryString.split(QUERY_STRING_DELIMITER);

        Arrays.stream(queryTokens).forEach(queryToken -> {
            String[] paramTokens = queryToken.split(PARAM_DELIMITER);
            String key = paramTokens[0];
            String value = paramTokens[1];

            try {
                queryParams.addParam(key, URLDecoder.decode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new ParamDecodeException(e);
            }
        });

        return queryParams;
    }
}
