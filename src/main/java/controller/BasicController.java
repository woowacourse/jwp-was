package controller;

import http.NotSupportedHttpMethodException;
import http.request.HttpMethodType;
import http.request.HttpRequest;
import http.response.HttpResponse;

public abstract class BasicController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (HttpMethodType.GET.equals(request.getHttpMethod())) {
            doGet(request, response);
            return;
        }
        if (HttpMethodType.POST.equals(request.getHttpMethod())) {
            doPost(request, response);
            return;
        }
        throw new NotSupportedHttpMethodException();
    }

    public abstract void doGet(HttpRequest request, HttpResponse response);

    public abstract void doPost(HttpRequest request, HttpResponse response);
}
