package controller.core;

import http.request.HttpRequest;

public interface Controller {
    boolean isMapping(HttpRequest httpRequest);

    int doController();
}
