package controller;

import webserver.message.request.Request;
import webserver.message.response.Response;

public interface Controller {
    Response service(final Request request);
}
