package controller.creator;

import controller.Controller2;
import http.request.Request2;

public interface Controller2Creator {
    Controller2 createController(Request2 request);
}
