package webserver.controller;

import webserver.Controller;
import webserver.HttpMethod;
import webserver.HttpRequest;
import webserver.exception.NotSupportedHttpMethodException;
import webserver.httpRequest.HttpStatus;

public abstract class AbstractController implements Controller {
    @Override
    public String service(HttpRequest request) {
        if (request.checkMethod(HttpMethod.GET)) {
            return doGet(request);
        }

        if (request.checkMethod(HttpMethod.POST)) {
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
