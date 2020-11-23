package controller;

import request.HttpRequest;
import response.HttpResponse;
import session.Session;

public class ControllerForTest implements Controller {

    @Override
    public HttpResponse service(HttpRequest httpRequest, Session session) {
        return null;
    }
}
