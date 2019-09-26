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
        CONTROLLERS.add(new UserLoginController());
    }

    public Controllers() {
    }

    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {

        RequestMapping requestMapping = RequestMapping.of(httpRequest.getHttpMethod(), httpRequest.getUri());

        CONTROLLERS.stream().filter(controller -> controller.isMapping(requestMapping))
            .findFirst()
            .ifPresentOrElse(controller -> controller.service(httpRequest,httpResponse), httpResponse::notFound);
    }
}