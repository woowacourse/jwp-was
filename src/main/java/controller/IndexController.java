package controller;

import http.request.HttpMethod;
import http.request.HttpRequest;
import http.request.HttpUriParser;
import http.response.HttpResponse;
import http.response.HttpStatus;
import http.response.ResponseBodyParser;
import http.response.StatusLine;
import org.apache.tika.Tika;

public class IndexController implements Controller {

    public static final String INDEX_PATH = "/index.html";
    private static final RequestMapping INDEX_REQUEST_MAPPING = RequestMapping.of(HttpMethod.GET, HttpUriParser.parse(INDEX_PATH));

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
        return INDEX_REQUEST_MAPPING.equals(requestMapping);
    }
}
