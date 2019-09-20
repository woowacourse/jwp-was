package webserver.handler.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public interface Controller {
    /**
     * 컨트롤러의 요청 처리 로직이다. 구현부에서 분기에 맞게 처리해주면 된다.
     * @param request HttpRequest
     * @param response HttpResponse
     */
    void service(HttpRequest request, HttpResponse response);
}
