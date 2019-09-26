package webserver.controller;

import webserver.controller.request.HttpRequest;
import webserver.controller.request.header.HttpMethod;
import webserver.controller.response.HttpResponse;

import java.util.HashMap;

public abstract class AbstractController implements Controller {
    private HashMap<HttpMethod, Controller> controllerMethods = new HashMap<>();

    AbstractController() {
        controllerMethods.put(HttpMethod.GET, this::doGet);
        controllerMethods.put(HttpMethod.POST, this::doPost);
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        controllerMethods.get(httpRequest.getHttpMethod()).service(httpRequest, httpResponse);
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            httpResponse.responseOK(httpRequest);
        } catch (Exception e) {
            httpResponse.responseBadRequest(e.getMessage());
        }
    };

    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) { };
}
