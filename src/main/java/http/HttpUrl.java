package http;

import java.util.Map;

public class HttpUrl {
    private static final String URL_DELIMITER = "\\?";
    private final String url;
    private final Parameters params;

    private HttpUrl(String url, Parameters params) {
        this.url = url;
        this.params = params;
    }

    public static HttpUrl from(String httpUrl) {
        String[] tokens = httpUrl.split(URL_DELIMITER);
        String url = tokens[0];
        if (tokens.length == 1) {
            return new HttpUrl(url, Parameters.parse());
        }
        return new HttpUrl(url, Parameters.parse(tokens[1]));
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
