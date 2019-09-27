package controller.methods;

import http.request.Request;
import http.response.Response;

import java.io.IOException;
import java.net.URISyntaxException;

public interface ControllerMethod {

    boolean isMapping(Request request);

    void processResponse(Request request, Response response) throws IOException, URISyntaxException;
}
