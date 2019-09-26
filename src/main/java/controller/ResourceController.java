package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.ResponseBodyParser;
import org.apache.tika.Tika;

public class ResourceController implements Controller {

    private static final String RESOURCE_FILE_REGEX = "^.+\\.([a-zA-Z]+)$";

    @Override
    public void service(final HttpRequest httpRequest,final HttpResponse httpResponse) {
        String filePath = httpRequest.findPathPrefix() + httpRequest.getPath();
        httpResponse.setResponseBody(ResponseBodyParser.parse(filePath));

        String contentType = new Tika().detect(filePath);
        httpResponse.putHeader("Content-Type", contentType);
        httpResponse.putHeader("Content-Length", Integer.toString(httpResponse.getBodyLength()));
        httpResponse.ok();
    }

    @Override
    public boolean isMapping(final RequestMapping requestMapping) {
        return requestMapping.isEquals(requestMapping, RESOURCE_FILE_REGEX);
    }
}
