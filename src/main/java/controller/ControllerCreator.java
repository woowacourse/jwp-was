package controller;

import http.request.Request;

public interface ControllerCreator {
    Controller create(Request request);
}
