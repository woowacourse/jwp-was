package webserver.http;

import java.util.Arrays;
import java.util.List;

public class HttpUrl {
    private static final int URL_INDEX = 0;
    private static final int PARAMS_INDEX = 1;

    private String url;
    private HttpRequestParams httpRequestParams;

    public HttpUrl(String url, HttpRequestParams httpRequestParams) {
        this.url = url;
        this.httpRequestParams = httpRequestParams;
    }

    public static HttpUrl from(String urlLine) {
        List<String> strings = Arrays.asList(urlLine.split("\\?"));
        String url = strings.get(URL_INDEX);
        String params = "";
        if (strings.size() > PARAMS_INDEX) {
            params = strings.get(PARAMS_INDEX);
        }

        return new HttpUrl(url, HttpRequestParams.from(params));
    }

    public String getUrl() {
        return url;
    }

    public HttpRequestParams getHttpRequestParams() {
        return httpRequestParams;
    }
}
