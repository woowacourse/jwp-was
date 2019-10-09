package controller;

import http.NotSupportedHttpMethodException;
import http.request.HttpMethodType;
import http.request.HttpRequest;
import http.response.HttpResponse;
import view.ModelAndView;
import webserver.resolver.BadRequestException;

public abstract class BasicController implements Controller {

    @Override
    public ModelAndView service(HttpRequest request, HttpResponse response) {
        if (HttpMethodType.GET.equals(request.getHttpMethod())) {
            return doGet(request, response);
        }
        if (HttpMethodType.POST.equals(request.getHttpMethod())) {
            return doPost(request, response);
        }
        throw new NotSupportedHttpMethodException();
    }

    protected ModelAndView doGet(HttpRequest request, HttpResponse response) {
        throw new BadRequestException();
    }

    protected ModelAndView doPost(HttpRequest request, HttpResponse response) {
        throw new BadRequestException();
    }
}
