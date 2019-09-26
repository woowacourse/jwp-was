package controller;

import http.request.HttpMethod;
import http.request.HttpRequest;
import http.request.HttpUriParser;
import http.response.HttpResponse;
import http.response.ResponseBodyParser;

public class IndexController implements Controller {

    public static final String INDEX_PATH = "/index.html";
    private static final RequestMapping INDEX_REQUEST_MAPPING = RequestMapping.of(HttpMethod.GET, HttpUriParser.parse(INDEX_PATH));

    @Override
    public void service(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        httpResponse.ok(ResponseBodyParser.parse(httpRequest.getClassPath()));
    }

    @Override
    public boolean isMapping(final RequestMapping requestMapping) {
        return INDEX_REQUEST_MAPPING.equals(requestMapping);
    }
}
