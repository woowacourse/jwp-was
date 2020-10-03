package webserver.controller;

import webserver.domain.request.HttpRequest;
import webserver.domain.response.HttpResponse;

abstract class AbstractController implements Controller {
    @Override
    public final HttpResponse service(HttpRequest httpRequest) {
        if (httpRequest.isGet()) {
            return get(httpRequest);
        }

        if (httpRequest.isPost()) {
            return post(httpRequest);
        }

        if (httpRequest.isPut()) {
            return put(httpRequest);
        }

        if (httpRequest.isDelete()) {
            return delete(httpRequest);
        }

        throw new UnsupportedOperationException("지원하지 않는 Http 요청 메서드입니다.");

    }

    protected HttpResponse get(HttpRequest httpRequest) {
        throw new UndefinedHttpRequestMethodException(httpRequest);
    }

    protected HttpResponse post(HttpRequest httpRequest) {
        throw new UndefinedHttpRequestMethodException(httpRequest);
    }

    protected HttpResponse put(HttpRequest httpRequest) {
        throw new UndefinedHttpRequestMethodException(httpRequest);
    }

    protected HttpResponse delete(HttpRequest httpRequest) {
        throw new UndefinedHttpRequestMethodException(httpRequest);
    }
}
