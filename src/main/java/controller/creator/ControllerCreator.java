package controller.creator;

import controller.Controller;
import http.request.Request2;

public interface ControllerCreator {

    Controller createController(Request2 request);
}
