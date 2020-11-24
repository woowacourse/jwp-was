package kr.wootecat.dongle.model.handler;

import kr.wootecat.dongle.model.http.request.HttpRequest;
import kr.wootecat.dongle.model.http.response.HttpResponse;

public class UnsupportedHandlerMapping implements HandlerMapping {
    @Override
    public boolean isSupport(HttpRequest request) {
        return false;
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response) {

    }
}
