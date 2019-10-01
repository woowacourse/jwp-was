package webserver.controller;

import webserver.View;
import webserver.http.HttpRequest;
import webserver.http.httpRequest.HttpStatus;

public class ErrorViewController extends AbstractController {

    private ErrorViewController() {

    }

    public static ErrorViewController getInstance() {
        return ErrorViewController.LazyHolder.INSTANCE;
    }

    protected View doGet(HttpRequest httpRequest) {
        return new View(ERROR_VIEW + HttpStatus.NOT_FOUND);
    }

    @Override
    protected View doPost(HttpRequest httpRequest) {
        return new View(ERROR_VIEW + HttpStatus.NOT_FOUND);
    }

    private static class LazyHolder {
        private static final ErrorViewController INSTANCE = new ErrorViewController();
    }
}
