package kr.wootecat.dongle.core.handler;

import kr.wootecat.dongle.http.request.HttpRequest;
import kr.wootecat.dongle.http.response.HttpResponse;

public interface HandlerMapping {

    boolean isSupport(HttpRequest request);

    void handle(HttpRequest request, HttpResponse response);
}
