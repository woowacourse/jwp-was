package kr.wootecat.dongle.core.handler;

import kr.wootecat.dongle.http.request.HttpRequest;
import kr.wootecat.dongle.http.response.HttpResponse;

public class UnsupportedHandlerMapping implements HandlerMapping {
    @Override
    public boolean isSupport(HttpRequest request) {
        return false;
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response) {

    }
}
