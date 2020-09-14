package web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import webserver.http.request.HttpRequest;
import webserver.http.request.RequestMethod;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;
import webserver.http.response.ResponseBody;
import webserver.http.response.ResponseHeaders;
import webserver.http.response.ResponseStatusLine;

public class PageController extends Controller {
    private static final String PATH = "./templates/";

    public HttpResponse viewPage(HttpRequest httpRequest) {
        if (httpRequest.getHttpStartLine().getHttpMethod() != RequestMethod.GET) {
            return notFound(httpRequest);
        }

        ResponseStatusLine responseStatusLine = new ResponseStatusLine(httpRequest.getHttpStartLine().getHttpVersion(),
            HttpStatus.OK);

        ResponseBody responseBody;
        try {
            responseBody = ResponseBody.ofFile(PATH + httpRequest.getHttpStartLine().getUrl());
        } catch (IOException e) {
            return notFound(httpRequest);
        }

        Map<String, String> headersInfo = new HashMap<>();
        headersInfo.put("Content-Type", "text/html;charset=utf-8");
        headersInfo.put("Content-Length", String.valueOf(responseBody.getBodyLength()));

        return new HttpResponse(responseStatusLine, new ResponseHeaders(headersInfo), responseBody);
    }
}
