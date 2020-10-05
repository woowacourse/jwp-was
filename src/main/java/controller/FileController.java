package controller;

import java.io.IOException;

import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.HttpStatus;

public class FileController extends AbstractController {
    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        httpResponse.setHttpStatus(HttpStatus.NOT_FOUND);
        httpResponse.error();
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        httpResponse.setHttpStatus(HttpStatus.METHOD_NOT_ALLOWED);
        httpResponse.error();
    }

    @Override
    public void doPut(HttpRequest httpRequest, HttpResponse httpResponse) {
    }

    @Override
    public void doDelete(HttpRequest httpRequest, HttpResponse httpResponse) {
    }
}
