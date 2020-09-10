package controller;

import java.io.IOException;
import java.net.URISyntaxException;

import http.request.Request;
import http.response.Response;
import utils.FileIoUtils;

public class FormController implements Controller {

    @Override
    public void run(Request request, Response response) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + request.getUri());
        response.response200Header(body.length);
        response.responseBody(body);
    }
}
