package webserver.controller;

import webserver.Controller;
import webserver.HttpRequest;
import webserver.exception.NotSupportedHttpMethodException;

public abstract class AbstractController implements Controller {
    @Override
    public String service(HttpRequest request) {
        if (request.getMethod().equals("GET")) {
            return doGet(request);
        }

        if (request.getMethod().equals("POST")) {
            return doPost(request);
        }

        throw new NotSupportedHttpMethodException();
    }

    protected abstract String doGet(HttpRequest httpRequest);

    protected abstract String doPost(HttpRequest httpRequest);
}
