package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

import java.util.ArrayList;
import java.util.List;

public class Controllers {

    private static final List<Controller> CONTROLLERS = new ArrayList<>();

    static {
        CONTROLLERS.add(new IndexController());
        CONTROLLERS.add(new UserCreateController());
        CONTROLLERS.add(new ResourceController());
    }

    public Controllers() {
    }

    public HttpResponse service(HttpRequest httpRequest) {
        RequestMapping requestMapping = RequestMapping.of(httpRequest.getHttpMethod(), httpRequest.getUri());

        return CONTROLLERS.stream().filter(controller -> controller.isMapping(requestMapping))
            .findFirst()
            .map(controller -> controller.service(httpRequest))
            .orElse(null);      // TODO: null -> 404 response로 변경
    }
}