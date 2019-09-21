package controller;

import http.request.HttpRequest;
import http.common.HttpHeader;
import http.response.HttpResponse;
import http.response.HttpStatus;
import http.response.Response200;
import http.response.ResponseBody;
import http.response.ResponseBodyParser;
import http.response.StatusLine;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IndexController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @Override
    HttpResponse doGet(HttpRequest httpRequest) {
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
    HttpResponse doPost(final HttpRequest httpRequest) {
        return null;
    }

    @Override
    HttpResponse doPut(final HttpRequest httpRequest) {
        return null;
    }

    @Override
    HttpResponse doDelete(final HttpRequest httpRequest) {
        return null;
    }
}
