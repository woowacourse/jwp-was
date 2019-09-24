package webserver.controller;

import webserver.Controller;
import webserver.HttpMethod;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.exception.NotSupportedHttpMethodException;

public abstract class AbstractController implements Controller {
    @Override
    public String service(HttpRequest request, HttpResponse response) {
        if (request.checkMethod(HttpMethod.GET)) {
            return doGet(request, response);
        }

        if (request.checkMethod(HttpMethod.POST)) {
            return doPost(request, response);
        }

        throw new NotSupportedHttpMethodException();
    }

    protected String doGet(HttpRequest httpRequest, HttpResponse response) {
        throw new NotSupportedHttpMethodException();
    }

    protected String doPost(HttpRequest httpRequest, HttpResponse response) {
        throw new NotSupportedHttpMethodException();
    }
}
