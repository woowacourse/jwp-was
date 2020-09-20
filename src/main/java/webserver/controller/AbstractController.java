package webserver.controller;

import webserver.http.request.HttpRequest;
import webserver.http.request.RequestMethod;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;

public abstract class AbstractController implements Controller {
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        if (httpRequest.getHttpMethod() == RequestMethod.GET) {
            doGet(httpRequest, httpResponse);
        } else if (httpRequest.getHttpMethod() == RequestMethod.POST) {
            doPost(httpRequest, httpResponse);
        } else {
            httpResponse.sendError(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 Http 메소드입니다.");
        }
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        httpResponse.sendError(HttpStatus.NOT_FOUND, "페이지가 없습니다.");
    }

    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        httpResponse.sendError(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 Http 메소드입니다.");
    }
}
