package webserver.http.request;

import java.util.Arrays;
import java.util.List;

public class RequestUrl {
    private static final int URL_INDEX = 0;
    private static final int PARAMS_INDEX = 1;

    private String url;
    private RequestParams requestParams;

    public RequestUrl(String url, RequestParams requestParams) {
        this.url = url;
        this.requestParams = requestParams;
    }

    public static RequestUrl from(String urlLine) {
        List<String> strings = Arrays.asList(urlLine.split("\\?"));
        String url = strings.get(URL_INDEX);
        String params = "";
        if (strings.size() > PARAMS_INDEX) {
            params = strings.get(PARAMS_INDEX);
        }

        return new RequestUrl(url, RequestParams.from(params));
    }

    public String getUrl() {
        return url;
    }

    public RequestParams getHttpRequestParams() {
        return requestParams;
    }
}
