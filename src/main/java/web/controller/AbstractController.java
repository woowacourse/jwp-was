package web.controller;

import web.http.HttpMethod;
import web.http.HttpRequest;
import web.http.HttpResponse;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        HttpMethod method = request.getMethod();

        if (HttpMethod.POST == method) {
            doPost(request, response);
        } else if (HttpMethod.GET == method) {
            doGet(request, response);
        }
    }

    protected abstract void doPost(HttpRequest request, HttpResponse response);

    protected abstract void doGet(HttpRequest request, HttpResponse response);
}
