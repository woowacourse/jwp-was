package web;

import java.io.IOException;
import java.net.URISyntaxException;

import webserver.HttpRequest;
import webserver.HttpResponse;

public interface Controller {
    void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException;
}
