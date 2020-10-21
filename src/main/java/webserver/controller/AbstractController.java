package webserver.controller;

import webserver.http.request.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public abstract class AbstractController implements Controller {

    @Override
    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (HttpMethod.GET.equals(httpRequest.getHttpMethod())) {
            doGet(httpRequest, httpResponse);
        }

        if (HttpMethod.POST.equals(httpRequest.getHttpMethod())) {
            doPost(httpRequest, httpResponse);
        }
    }

    public abstract void doGet(HttpRequest httpRequest, HttpResponse httpResponse);

    public abstract void doPost(HttpRequest httpRequest, HttpResponse httpResponse);
}
