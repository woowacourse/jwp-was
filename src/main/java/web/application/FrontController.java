package web.application;

import java.util.HashMap;
import java.util.Map;
import web.application.controller.Controller;
import web.application.controller.CreateUserController;
import web.application.controller.PageNotFoundController;
import web.application.controller.RootController;
import web.application.controller.StaticController;
import web.application.vo.RequestVo;
import web.server.domain.request.HttpRequest;
import web.server.domain.request.RequestMethod;
import web.server.domain.response.HttpResponse;

public class FrontController implements Controller {

    private final Map<RequestVo, Controller> mapper;

    public FrontController() {
        mapper = new HashMap<>();
        mapper.put(RequestVo.of("/user/create", RequestMethod.POST), CreateUserController.getInstance());
        mapper.put(RequestVo.of("/", RequestMethod.GET), RootController.getInstance());
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.hasPathOfStaticFile()) {
            StaticController.getInstance().service(httpRequest, httpResponse);
            return;
        }
        RequestVo requestVo = RequestVo.of(httpRequest.getPath(), httpRequest.getRequestMethod());
        if (mapper.containsKey(requestVo)) {
            mapper.get(requestVo).service(httpRequest, httpResponse);
            return;
        }
        PageNotFoundController.getInstance().service(httpRequest, httpResponse);
    }
}
