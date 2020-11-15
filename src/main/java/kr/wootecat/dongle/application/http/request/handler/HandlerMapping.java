package kr.wootecat.dongle.application.http.request.handler;

import kr.wootecat.dongle.application.http.request.HttpRequest;
import kr.wootecat.dongle.application.http.response.HttpResponse;

public interface HandlerMapping {

    boolean isSupport(HttpRequest request);

    void handle(HttpRequest request, HttpResponse response);
}
