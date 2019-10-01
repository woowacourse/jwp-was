package webserver.controller;

import http.NotSupportedHttpMethodException;
import http.request.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;

public class AbstractController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (HttpMethod.GET.equals(request.getHttpMethod())) {
            doGet(request, response);
            return;
        }
        if (HttpMethod.POST.equals(request.getHttpMethod())) {
            doPost(request, response);
            return;
        }
        throw new NotSupportedHttpMethodException();
    }

    public void doGet(HttpRequest request, HttpResponse response) {
        throw new NotSupportedHttpMethodException();
    }

    public void doPost(HttpRequest request, HttpResponse response) {
        throw new NotSupportedHttpMethodException();
    }
}
