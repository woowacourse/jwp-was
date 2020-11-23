package kr.wootecat.dongle.model.handler;

import kr.wootecat.dongle.model.http.request.HttpRequest;
import kr.wootecat.dongle.model.http.response.HttpResponse;

public interface HandlerMapping {

    boolean isSupport(HttpRequest request);

    void handle(HttpRequest request, HttpResponse response);
}
