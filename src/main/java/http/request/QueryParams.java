package http.request;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static java.net.URLDecoder.decode;

public class QueryParams {
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final int URI_INDEX = 1;
    private static final int CORRECT_PARAMS_SIZE = 2;
    private static final String QUERY_PARAMS_DELIMITER = "\\?";
    private static final String PARAM_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final String EMPTY = "";
    private static final String REQUEST_LINE_DELIMITER = " ";

    private Map<String, String> params;

    private QueryParams(Map<String, String> params) {
        this.params = params;
    }

    public static QueryParams parseRequestLine(String requestLine) throws UnsupportedEncodingException {
        String uri = requestLine.split(REQUEST_LINE_DELIMITER)[URI_INDEX];
        String decodedUri = decode(uri, StandardCharsets.UTF_8.name());
        String[] splicedUri = decodedUri.split(QUERY_PARAMS_DELIMITER);
        String queryString = splicedUri[splicedUri.length - 1];

        return QueryParams.of(queryString);
    }

    public static QueryParams parseBody(String body) {
        return QueryParams.of(body);
    }

    private static QueryParams of(String query) {
        return new QueryParams(splitQueryParams(query));
    }

    private static Map<String, String> splitQueryParams(String query) {
        Map<String, String> params = new HashMap<>();

        for (String param : query.split(PARAM_DELIMITER)) {
            String[] splicedParam = param.split(KEY_VALUE_DELIMITER);
            params.put(splicedParam[KEY_INDEX], extractValueFrom(splicedParam));
        }
        return params;
    }

    private static String extractValueFrom(String[] splicedParam) {
        return (splicedParam.length != CORRECT_PARAMS_SIZE)
                ? EMPTY
                : splicedParam[VALUE_INDEX];
    }

    public static boolean canParse(String requestLine) {
        String[] splicedRequestLine = requestLine.split(REQUEST_LINE_DELIMITER);
        String uri = splicedRequestLine[URI_INDEX];

        return uri.split(QUERY_PARAMS_DELIMITER).length == 2;
    }

    public String getParam(String key) {
        return params.get(key);
    }

    @Override
    public String toString() {
        return "QueryParams{" +
                "params=" + params +
                '}';
    }
}
