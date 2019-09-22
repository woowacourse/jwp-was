package controller;

import model.HttpMethod;
import model.Request;
import model.Response;

public abstract class AbstractController implements Controller {
    @Override
    public void service(Request request, Response response) {
        if (request.getMethod().contains(HttpMethod.POST.name())) {
            doPost(request,response);
        }
        if (request.getMethod().contains(HttpMethod.GET.name())) {
            doGet(request,response);
        }
    }

    abstract void doPost(Request request, Response response);

    abstract void doGet(Request request, Response response);
}
