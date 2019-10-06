package webserver.controller;

import exception.UnsupportedMethodException;
import webserver.controller.request.HttpRequest;
import webserver.controller.request.header.HttpMethod;
import webserver.controller.response.HttpResponse;

import java.io.IOException;
import java.util.HashMap;

public abstract class AbstractController implements Controller {
    private HashMap<HttpMethod, Controller> controllerMethods = new HashMap<>();

    AbstractController() {
        controllerMethods.put(HttpMethod.GET, this::doGet);
        controllerMethods.put(HttpMethod.POST, this::doPost);
    }

    @Override
    public HttpResponse service(HttpRequest httpRequest) throws IOException{
        return controllerMethods.get(httpRequest.getHttpMethod()).service(httpRequest);
    }

    protected HttpResponse doGet(HttpRequest httpRequest) throws IOException {
        throw new UnsupportedMethodException();
    }

    protected HttpResponse doPost(HttpRequest httpRequest) {
        throw new UnsupportedMethodException();
    }

}
