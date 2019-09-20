package webserver.handler;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.view.ModelAndView;

public interface Handler {
    /**
     * 실제 요청을 invoke 하는 부분을 구현해주면 된다.
     * @param httpRequest HttpRequest
     * @param httpResponse HttpResponse
     * @return
     */
    ModelAndView handle(HttpRequest httpRequest, HttpResponse httpResponse);

    /**
     * 요청에 맞는 핸들러를 반환하는 부분을 구현해주면 된다.
     * @param path 사용자의 요청의 전체 경로
     * @return
     */
    Handler getHandler(String path);
}
