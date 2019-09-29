package webserver.controller;

import webserver.Controller;
import webserver.View;
import webserver.exception.NotSupportedHttpMethodException;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;

public abstract class AbstractController implements Controller {
    public static final String REDIRECT_VIEW = "/redirect:";
    public static final String ERROR_VIEW = "/error:";

    @Override
    public View service(HttpRequest request) {
        if (request.checkMethod(HttpMethod.GET)) {
            return doGet(request);
        }

        if (request.checkMethod(HttpMethod.POST)) {
            return doPost(request);
        }

        throw new NotSupportedHttpMethodException();
    }

    protected View doGet(HttpRequest httpRequest) {
        throw new NotSupportedHttpMethodException();
    }

    protected View doPost(HttpRequest httpRequest) {
        throw new NotSupportedHttpMethodException();
    }
}
