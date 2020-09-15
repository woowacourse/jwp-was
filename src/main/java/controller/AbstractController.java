package controller;

import http.request.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;

public abstract class AbstractController implements Controller {
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (HttpMethod.GET.equals(httpRequest.getMethod())) {
            doGet(httpRequest, httpResponse);
        }
        if (HttpMethod.POST.equals(httpRequest.getMethod())) {
            doPost(httpRequest, httpResponse);
        }
    }

    abstract void doPost(HttpRequest httpRequest, HttpResponse httpResponse);

    abstract void doGet(HttpRequest httpRequest, HttpResponse httpResponse);
}
