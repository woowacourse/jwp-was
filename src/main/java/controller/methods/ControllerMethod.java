package controller.methods;

import http.request.Request;
import http.response.Response;

import java.io.IOException;

public interface ControllerMethod {

    boolean isMapping(Request request);

    void processResponse(Request request, Response response) throws IOException;
}
