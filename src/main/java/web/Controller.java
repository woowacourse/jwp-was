package web;

import java.util.HashMap;
import java.util.Map;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;
import webserver.http.response.ResponseBody;
import webserver.http.response.ResponseHeaders;
import webserver.http.response.ResponseStatusLine;

public abstract class Controller {
    protected HttpResponse notFound(HttpRequest httpRequest) {
        ResponseStatusLine responseStatusLine = new ResponseStatusLine(httpRequest.getHttpStartLine().getHttpVersion(),
            HttpStatus.NOT_FOUND);
        Map<String, String> headersInfo = new HashMap<>();
        ResponseBody responseBody = new ResponseBody("페이지가 없습니다.");
        headersInfo.put("Content-Type", "text/html;charset=utf-8");
        headersInfo.put("Content-Length", String.valueOf(responseBody.getBodyLength()));
        return new HttpResponse(responseStatusLine, new ResponseHeaders(headersInfo), responseBody);
    }
}
