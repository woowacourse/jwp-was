package controller.core;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

public interface Controller {
    void service(OutputStream out, HttpRequest httpRequest, HttpResponse response) throws IOException, URISyntaxException;
}
