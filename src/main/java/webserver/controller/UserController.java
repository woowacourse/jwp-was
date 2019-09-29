package webserver.controller;

import webserver.HttpStatus;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.FileNotFoundException;

public class UserController extends AbstractController {

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws FileNotFoundException {
        byte[] staticFile = getStaticFile(httpRequest);
        httpResponse.addStatusLine(httpRequest, HttpStatus.OK);
        httpResponse.addHeader(HEADER_FIELD_CONTENT_TYPE, CONTENT_TYPE_HTML);
        httpResponse.addHeader(HEADER_FIELD_CONTENT_LENGTH, String.valueOf(staticFile.length));
        httpResponse.addBody(staticFile);
    }
}
