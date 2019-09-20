package controller;

import model.Request;
import model.Response;

public abstract class AbstractController implements Controller {
    @Override
    public void service(Request request, Response response) {
        if (request.getMethod().contains("POST")) {
            doPost(request,response);
        }
        if (request.getMethod().contains("GET")) {
            doGet(request,response);
        }
    }

    abstract void doPost(Request request, Response response);

    abstract void doGet(Request request, Response response);
}
