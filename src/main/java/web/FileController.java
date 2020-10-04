package web;

import java.io.IOException;

import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.HttpStatus;

public class FileController extends AbstractController {
    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        //todo : throw exception
        httpResponse.setHttpStatus(HttpStatus.FOUND);
        httpResponse.sendRedirect("/index.html");
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {

    }

    @Override
    public void doPut(HttpRequest httpRequest, HttpResponse httpResponse) {

    }

    @Override
    public void doDelete(HttpRequest httpRequest, HttpResponse httpResponse) {

    }
}
