package controller;

import http.parser.ResponseBodyParser;
import http.request.HttpRequest;
import http.response.HttpResponse;

public class ResourceController implements Controller {

    private static final String RESOURCE_FILE_REGEX = "^.+\\.([a-zA-Z]+)$";

    @Override
    public void service(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        httpResponse.forward(ResponseBodyParser.parse(httpRequest.getClassPath()));
    }

    @Override
    public boolean isMapping(final RequestMapping requestMapping) {
        return requestMapping.isEquals(requestMapping, RESOURCE_FILE_REGEX);
    }
}
