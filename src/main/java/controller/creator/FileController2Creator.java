package controller.creator;

import controller.Controller2;
import controller.FileController2;
import http.request.Request2;

public class FileController2Creator implements Controller2Creator {

    @Override
    public Controller2 createController(Request2 request) {
        return new FileController2(request);
    }
}
