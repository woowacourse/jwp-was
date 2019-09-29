package webserver.controller;

import exception.UnsupportedMethodException;
import webserver.controller.request.HttpRequest;
import webserver.controller.request.header.HttpMethod;
import webserver.controller.response.HttpResponse;

import java.util.HashMap;

public abstract class AbstractController implements Controller {
    public static final String NON_STATIC_FILE_PATH = "./templates/";
    private HashMap<HttpMethod, Controller> controllerMethods = new HashMap<>();

    AbstractController() {
        controllerMethods.put(HttpMethod.GET, this::doGet);
        controllerMethods.put(HttpMethod.POST, this::doPost);
    }

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        return controllerMethods.get(httpRequest.getHttpMethod()).service(httpRequest);
    }

    protected HttpResponse doGet(HttpRequest httpRequest) {
        throw new UnsupportedMethodException();
    }

    protected HttpResponse doPost(HttpRequest httpRequest) {
        throw new UnsupportedMethodException();
    };
}
