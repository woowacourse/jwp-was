package controller;

import controller.exception.MethodNotAllowedException;
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
    public ModelAndView service(HttpRequest httpRequest, HttpResponse httpResponse) {
        return methodMap.get(httpRequest.getMethod()).service(httpRequest, httpResponse);
    }

    public ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new MethodNotAllowedException();
    }

    public ModelAndView doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new MethodNotAllowedException();
    }
}