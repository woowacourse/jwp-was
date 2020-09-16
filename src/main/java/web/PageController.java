package web;

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
    public HttpResponse viewFile(HttpRequest httpRequest) {
        if (httpRequest.getHttpStartLine().getHttpMethod() != RequestMethod.GET) {
            return notAllowed(httpRequest);
        }

        ResponseStatusLine responseStatusLine = new ResponseStatusLine(httpRequest.getHttpStartLine().getHttpVersion(),
            HttpStatus.OK);

        Map<String, String> headerInfo = new HashMap<>();
        ResponseBody responseBody;
        try {
            FileMapping fileMapping = FileMapping.findByExtension(httpRequest.getHttpStartLine().getUrl());
            headerInfo.put("Content-Type", fileMapping.getContentType());
            responseBody = ResponseBody.ofFile(fileMapping.getFilePath() + httpRequest.getHttpStartLine().getUrl());
        } catch (Exception e) {
            return notFound(httpRequest);
        }

        headerInfo.put("Content-Length", String.valueOf(responseBody.getBodyLength()));

        return new HttpResponse(responseStatusLine, new ResponseHeaders(headerInfo), responseBody);
    }
}
