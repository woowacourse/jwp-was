package webserver.controller;

import webserver.Controller;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.HttpStatus;

public class MainController implements Controller {
    private static final MainController INSTANCE = new MainController();

    public static MainController getInstance() {
        return INSTANCE;
    }

    @Override
    public Object service(HttpRequest request, HttpResponse response) {
        if (request.isGet()) {
            return doGet(request, response);
        }

        if (request.isPost()) {
            return doPost(request, response);
        }
        throw new IllegalArgumentException();
    }

    private Object doGet(HttpRequest request, HttpResponse response) {

        response.setHttpStatus(HttpStatus.OK);
    }

    private Object doPost(HttpRequest request, HttpResponse response) {

    }
}
