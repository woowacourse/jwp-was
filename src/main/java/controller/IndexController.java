package controller;

import http.common.HttpHeader;
import http.request.HttpMethod;
import http.request.HttpRequest;
import http.request.HttpUriParser;
import http.response.HttpResponse;
import http.response.HttpStatus;
import http.response.Response200;
import http.response.ResponseBody;
import http.response.ResponseBodyParser;
import http.response.StatusLine;
import org.apache.tika.Tika;

public class IndexController implements Controller {

    public static final String INDEX_PATH = "/index.html";
    private static final RequestMapping INDEX_REQUEST_MAPPING = RequestMapping.of(HttpMethod.GET, HttpUriParser.parse(INDEX_PATH));

    @Override
    public HttpResponse service(final HttpRequest httpRequest) {
        StatusLine statusLine = new StatusLine(httpRequest.getHttpVersion(), HttpStatus.OK);

        String filePath = httpRequest.findPathPrefix() + httpRequest.getPath();
        ResponseBody responseBody = ResponseBodyParser.parse(filePath);

        String contentType = new Tika().detect(filePath);
        HttpHeader responseHeader = new HttpHeader();
        responseHeader.putHeader("Content-Type", contentType);
        responseHeader.putHeader("Content-Length", Integer.toString(responseBody.getLength()));
        return new Response200(statusLine, responseHeader, responseBody);
    }

    @Override
    public boolean isMapping(final RequestMapping requestMapping) {
        return INDEX_REQUEST_MAPPING.equals(requestMapping);
    }
}
