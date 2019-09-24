package controller;

import http.request.HttpRequest;
import http.request.RequestMethod;
import http.response.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractController implements Controller {
    private final Map<RequestMethod, Controller> methodMap = new HashMap<>();

    {
        methodMap.put(RequestMethod.GET, this::doGet);
        methodMap.put(RequestMethod.POST, this::doPost);
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        methodMap.get(httpRequest.getMethod()).service(httpRequest, httpResponse);
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {

    }

    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {

    }
}
