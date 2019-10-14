package web.controller;

import webserver.message.request.Request;
import webserver.message.response.Response;

public interface Controller {
    void service(final Request request, final Response response);
}
