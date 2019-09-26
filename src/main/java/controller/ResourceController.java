package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpStatus;
import http.response.ResponseBodyParser;
import http.response.StatusLine;
import org.apache.tika.Tika;

public class ResourceController implements Controller {

    private static final String RESOURCE_FILE_REGEX = "^.+\\.([a-zA-Z]+)$";

    @Override
    public void service(final HttpRequest httpRequest,final HttpResponse httpResponse) {
        httpResponse.setStatusLine(new StatusLine(httpRequest.getHttpVersion(), HttpStatus.OK));

        String filePath = httpRequest.findPathPrefix() + httpRequest.getPath();
        httpResponse.setResponseBody(ResponseBodyParser.parse(filePath));

        String contentType = new Tika().detect(filePath);
        httpResponse.putHeader("Content-Type", contentType);
        httpResponse.putHeader("Content-Length", Integer.toString(httpResponse.getBodyLength()));
    }

    @Override
    public boolean isMapping(final RequestMapping requestMapping) {
        return requestMapping.isEquals(requestMapping, RESOURCE_FILE_REGEX);
    }
}
