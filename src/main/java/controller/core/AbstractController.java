package controller.core;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class AbstractController implements Controller {
    protected static final String SESSION_USER_KEY = "user";

    @Override
    public abstract void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException;

    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
    }

    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    }
}
