package http;

import java.util.Map;

public class HttpUrl {
    private static final String URL_DELIMITER = "\\?";
    private static final int URL_INDEX = 0;
    private static final int PARAMS_INDEX = 1;
    private final String url;
    private final Parameters params;

    private HttpUrl(String url, Parameters params) {
        this.url = url;
        this.params = params;
    }

    public static HttpUrl from(String httpUrl) {
        String[] tokens = httpUrl.split(URL_DELIMITER);
        String url = tokens[URL_INDEX];
        if (existQueryParam(tokens)) {
            return new HttpUrl(url, Parameters.parse(tokens[PARAMS_INDEX]));
        }
        return new HttpUrl(url, Parameters.parse());
    }

    private static boolean existQueryParam(String[] tokens) {
        return tokens.length == 2;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getParams() {
        return params.getParams();
    }

    public String getParam(String param) {
        return params.get(param);
    }
}
