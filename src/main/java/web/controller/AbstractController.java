package web.controller;

import web.HttpMethod;
import web.HttpRequest;
import web.HttpResponse;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        HttpMethod method = request.getMethod();

        if (HttpMethod.POST == method) {
            doPost(request, response);
        } else if (HttpMethod.GET == method) {
            doGet(request, response);
        } else if (HttpMethod.NONE == method) {
            response.response405Header();
        }
    }

    protected abstract void doPost(HttpRequest request, HttpResponse response);

    protected abstract void doGet(HttpRequest request, HttpResponse response);
}
