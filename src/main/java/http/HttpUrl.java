package http;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpUrl {
    private static final String TEMPLATES_FILE_PATH = "./templates";
    private static final String URL_DELIMITER = "\\?";
    private static final String PARAMS_DELIMITER = "&";
    private static final String PARAM_DELIMITER = "=";
    private static final int PARAMETER_NAME_INDEX = 0;
    private static final int PARAMETER_VALUE_INDEX = 1;
    private final String url;
    private final Map<String, String> params;

    private HttpUrl(String url, Map<String, String> params) {
        this.url = url;
        this.params = params;
    }

    public static HttpUrl from(String httpUrl) {
        String[] tokens = httpUrl.split(URL_DELIMITER);
        String url = tokens[0];
        if (tokens.length == 1) {
            return new HttpUrl(url, new HashMap<>());
        }
        Map<String, String> params = extractParams(tokens[1]);
        return new HttpUrl(url, params);
    }

    private static Map<String, String> extractParams(String token) {
        Map<String, String> params = new HashMap<>();
        System.out.println(token);
        for(String param: token.split(PARAMS_DELIMITER)) {
            String[] paramTokens = param.split(PARAM_DELIMITER);
            params.put(paramTokens[PARAMETER_NAME_INDEX], paramTokens[PARAMETER_VALUE_INDEX]);
        }
        System.out.println(params.toString());
        return params;
    }

    public String extractFilePath() {
        return TEMPLATES_FILE_PATH + this.url;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public String getParam(String param) {
        return params.get(param);
    }
}
