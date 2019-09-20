package controller;

import webserver.Controller;
import webserver.HttpMethod;
import webserver.HttpRequest;
import webserver.HttpResponse;

public abstract class AbstractController implements Controller {

    public abstract void doGet(HttpRequest req, HttpResponse res);

    public abstract void doPost(HttpRequest req, HttpResponse res);

    protected UnsupportedOperationException createUnsupportedException() {
        return new UnsupportedOperationException("Controller for this request is not implemented");
    }

    @Override
    public void service(HttpRequest req, HttpResponse res) {
        if (req.matchMethod(HttpMethod.GET)) {
            doGet(req, res);
        }
        if (req.matchMethod(HttpMethod.POST)) {
            doPost(req, res);
        }
    }
}
