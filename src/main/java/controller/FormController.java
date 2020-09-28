package controller;

import java.io.IOException;
import java.net.URISyntaxException;

import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.FileIoUtils;

public class FormController implements Controller {

    @Override
    public void run(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + httpRequest.getUri());
        httpResponse.response200Header(httpRequest.getHeaderByName("Accept").split(",")[0], body.length);
        httpResponse.responseBody(body);
    }
}
