package controller.creator;

import controller.Controller;
import http.request.Request;

public interface ControllerCreator {

    Controller createController(Request request);
}
