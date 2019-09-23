package controller;

import model.HttpMethod;
import model.HttpRequest;
import model.HttpResponse;

public abstract class AbstractController implements Controller {
    @Override
    public void service(HttpRequest request, HttpResponse httpResponse) {
        if (request.getMethod().contains(HttpMethod.POST.name())) {
            doPost(request, httpResponse);
        }
        if (request.getMethod().contains(HttpMethod.GET.name())) {
            doGet(request, httpResponse);
        }
    }

    public void doPost(HttpRequest request, HttpResponse httpResponse) {
        httpResponse.response405();
    }

    public void doGet(HttpRequest request, HttpResponse httpResponse) {
        httpResponse.response405();
    }
}
