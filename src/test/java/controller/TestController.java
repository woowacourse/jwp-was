package controller;

import web.request.HttpRequest;
import web.response.HttpResponse;

/**
 * TestController는 테스트 코드에서만 사용되는 클래스이다.
 * AbstractController의 메인 로직(service, doGet, doPost)을 그대로 사용한다.
 * 다만 정의되지 않은 AbstractController의 get과 post를 호출하는 메서드가 추가되었다.
 * 이 메서드들을 통해, 정의되지 않은 요청이 호출될 시 400 Bad Request가 정상적으로 발동되는지 확인할 수 있다.
 */
public class TestController extends AbstractController {
    public void callGetMethod(HttpRequest request, HttpResponse response) {
        doGet(request, response);
    }

    public void callPostMethod(HttpRequest request, HttpResponse response) {
        doPost(request, response);
    }
};
