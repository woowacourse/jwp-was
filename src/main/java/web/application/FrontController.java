package web.application;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import web.application.controller.CreateUserController;
import web.application.controller.RootController;
import web.application.exception.ControllerNotFoundException;
import web.application.vo.RequestVo;
import web.server.domain.request.HttpRequest;
import web.server.domain.request.RequestMethod;
import web.server.domain.response.HttpResponse;

public class FrontController {

    private static final Map<RequestVo, BiConsumer<HttpRequest, HttpResponse>> mapper;

    static {
        mapper = new HashMap<>();
        mapper.put(RequestVo.of("/user/create", RequestMethod.POST), CreateUserController.getInstance()::doPost);
        mapper.put(RequestVo.of("/", RequestMethod.GET), RootController.getInstance()::doGet);
    }

    public static BiConsumer<HttpRequest, HttpResponse> findMatchingController(RequestVo requestVo) {
        if (mapper.containsKey(requestVo)) {
            return mapper.get(requestVo);
        }
        throw new ControllerNotFoundException(requestVo);
    }
}
