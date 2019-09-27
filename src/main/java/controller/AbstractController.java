package controller;

import http.request.HttpRequest;
import http.request.RequestMethod;
import http.response.HttpResponse;
import http.response.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractController implements Controller {
    private final Map<RequestMethod, Controller> methodMap = new HashMap<>();
    private final ViewResolver viewResolver = ViewResolver.getInstance();

    {
        methodMap.put(RequestMethod.GET, this::doGet);
        methodMap.put(RequestMethod.POST, this::doPost);
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        methodMap.get(httpRequest.getMethod()).service(httpRequest, httpResponse);
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.setResponseStatus(ResponseStatus.METHOD_NOT_ALLOWED);
    }

    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.setResponseStatus(ResponseStatus.METHOD_NOT_ALLOWED);
    }

    protected void handle(ModelAndView modelAndView, HttpResponse httpResponse) {
        View view = viewResolver.resolve(modelAndView.getViewName());
        view.render(modelAndView.getModelMap(), httpResponse);
    }
}
