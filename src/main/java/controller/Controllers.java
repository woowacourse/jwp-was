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

    public HttpResponse service(HttpRequest httpRequest) {
        RequestMapping requestMapping = RequestMapping.of(httpRequest.getHttpMethod(), httpRequest.getUri());

        for (final Controller controller : CONTROLLERS) {
            if(controller.isMapping(requestMapping)){
                return controller.service(httpRequest);
            }
        }
        return null;

//        return CONTROLLERS.stream().filter(controller -> controller.isMapping(requestMapping))
//            .findFirst()
//            .map(controller -> controller.service(httpRequest))
//            .orElse(null);      // TODO: null -> 405 response로 변경
    }
}