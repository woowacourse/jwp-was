package http.request;

import com.google.common.base.Strings;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestParams {
    private static final String QUERY_DELIMITER = "&";
    private static final String PARAM_DELIMITER = "=";

    private Map<String, String> params;

    public HttpRequestParams(Map<String, String> params) {
        this.params = params;
    }

    public static HttpRequestParams of() {
        return new HttpRequestParams(new HashMap<>());
    }

    public String getParameter(String name) {
        return params.get(name);
    }

    public void putAll(String queryString) throws IOException {
        params.putAll(parseQuery(queryString));
    }

    public static Map<String, String> parseQuery(String queryString) throws IOException {
        Map<String, String> result = new HashMap<>();
        if (Strings.isNullOrEmpty(queryString)) {
            return result;
        }

        for (String q : queryString.split(URLDecoder.decode(QUERY_DELIMITER, "UTF-8"))) {
            String[] token = q.split(PARAM_DELIMITER);
            result.put(token[0], token[1]);
        }

        return result;
    }
}
