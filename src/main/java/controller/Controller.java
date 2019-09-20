package controller;

import model.Request;
import model.Response;

public interface Controller {
    void service(Request request, Response response);
}
