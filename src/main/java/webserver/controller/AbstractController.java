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

    protected String doGet(HttpRequest httpRequest) {
        throw new NotSupportedHttpMethodException();
    }

    protected String doPost(HttpRequest httpRequest) {
        throw new NotSupportedHttpMethodException();
    }
}
