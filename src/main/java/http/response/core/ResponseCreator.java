package http.response.core;

import http.request.HttpRequest;

public interface ResponseCreator {
    Response create(HttpRequest httpRequest);
}
