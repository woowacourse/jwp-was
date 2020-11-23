package kr.wootecat.dongle.model.handler;

import kr.wootecat.dongle.model.http.request.HttpRequest;
import kr.wootecat.dongle.model.http.response.HttpResponse;

public class SupportedHandlerMapping implements HandlerMapping {
    @Override
    public boolean isSupport(HttpRequest request) {
        return true;
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response) {

    }
}
