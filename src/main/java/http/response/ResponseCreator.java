package http.response;

import http.request.Request;

public interface ResponseCreator {
    Response create(Request request);
}
