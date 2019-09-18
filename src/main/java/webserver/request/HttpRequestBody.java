package webserver.request;

import webserver.request.requestline.QueryParams;

public class HttpRequestBody {

    private QueryParams queryParams;

    public HttpRequestBody(final QueryParams queryParams) {
        this.queryParams = queryParams;
    }

    public String findQueryParam(String key) {
        return queryParams.findParam(key);
    }
}
