package controller;

import http.request.Request;
import http.response.Response;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class ExceptionController implements Controller {

    @Override
    public boolean isMapping(ControllerMapper controllerMapper) {
        return false;
    }

    @Override
    public void processResponse(Request request, Response response) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath("../resources/templates/error.html");

        response.ok()
                .putResponseHeaders("Content-Type: ", request.getUrl().getRequestContentType().getContentType())
                .body(body);
    }
}
