package web.controller;

import web.http.HttpMethod;
import web.http.HttpRequest;
import web.http.HttpResponse;
import web.http.HttpStatus;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        HttpMethod method = request.getMethod();

        if (HttpMethod.POST == method) {
            doPost(request, response);
        } else if (HttpMethod.GET == method) {
            doGet(request, response);
        }
    }

    protected void doPost(HttpRequest request, HttpResponse response) {
        notAllowedMethod(response);
    }

    protected void doGet(HttpRequest request, HttpResponse response) {
        notAllowedMethod(response);
    }

    private void notAllowedMethod(HttpResponse response) {
        response.addHttpStatus(HttpStatus.NOT_ALLOWED_METHOD);
        ExceptionHandler.processException(new NoSuchMethodException("지원하지 않는 메서드입니다."), response);
    }
}
