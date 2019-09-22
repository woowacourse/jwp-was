package webserver.controller.request;

import webserver.controller.AbstractController;
import webserver.controller.UserController;
import webserver.controller.response.HttpResponse;

import java.util.HashMap;
import java.util.function.BiConsumer;

public class RequestMapper {
    private static final HashMap<String, BiConsumer<HttpRequest, HttpResponse>> requestMapper = new HashMap<>();

    static {
        requestMapper.put("/index.html", AbstractController::movePage);
        requestMapper.put("/user/form.html", AbstractController::movePage);
        requestMapper.put("/user/create", UserController::save);
        requestMapper.put("STATIC_FILE", AbstractController::movePage);
    }

    public void executeMapping(HttpRequest httpRequest, HttpResponse httpResponse) {

        String path = httpRequest.getPath();
        System.out.println("PATH : >>>" + path);
        if (requestMapper.containsKey(path)) {
            requestMapper.get(path).accept(httpRequest, httpResponse);
            return;
        }
        requestMapper.get("STATIC_FILE").accept(httpRequest, httpResponse);
    }
}
