package webserver.controller;

import webserver.Controller;
import webserver.View;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.httpRequest.HttpStatus;

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

        return new View(ERROR_VIEW + HttpStatus.METHOD_NOT_ALLOWED);
    }

    protected View doGet(HttpRequest httpRequest) {
        return new View(ERROR_VIEW + HttpStatus.METHOD_NOT_ALLOWED);
    }

    protected View doPost(HttpRequest httpRequest) {
        return new View(ERROR_VIEW + HttpStatus.METHOD_NOT_ALLOWED);
    }
}
