package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Controllers {

    private static final List<Controller> CONTROLLERS = new ArrayList<>();

    static {
        CONTROLLERS.add(new IndexController());
        CONTROLLERS.add(new UserSignUpController());
        CONTROLLERS.add(new ResourceController());
        CONTROLLERS.add(new UserLoginController());
        CONTROLLERS.add(new UserListController());
    }

    public Controllers() {
    }

    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {

        RequestMapping requestMapping = RequestMapping.of(httpRequest.getHttpMethod(), httpRequest.getUri());

        Optional<Controller> FoundController = CONTROLLERS.stream().filter(controller -> controller.isMapping(requestMapping))
            .findFirst();

        if (FoundController.isPresent()) {
            FoundController.get().service(httpRequest, httpResponse);
            return;
        }

        // Todo : 404 status 넘기기
        httpResponse.error();
    }
}