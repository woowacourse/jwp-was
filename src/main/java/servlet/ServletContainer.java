package servlet;

import http.response.HttpResponse;
import http.request.HttpRequest;
import servlet.controller.Controller;
import servlet.controller.ControllerFinder;

import java.io.IOException;
import java.net.URISyntaxException;

public class ServletContainer {
    private final ControllerFinder controllerMapper;

    public ServletContainer(final ControllerFinder controllerMapper) {
        this.controllerMapper = controllerMapper;
    }

    public HttpResponse process(
            final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException, URISyntaxException {
        Controller controller = controllerMapper.find(httpRequest);
        controller.service(httpRequest, httpResponse);
        // ServletHttpResponse 으로

        // view가 HttpResponse를 인자로 받는다
        return httpResponse;
    }

    // HttpRequest => HttpServletRequest
    // HttpServletRequest는 getCookie(), getHeader()
    // HttpServletResponse는 Redirect나 Forward를 결정해주고 Model을 집어넣어주는 구조가 되고 쿠키를 설정해주는게 될듯 싶다
}
