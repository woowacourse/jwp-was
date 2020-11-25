package was.webserver.controller;

import was.webserver.http.request.HttpRequest;
import was.webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Controller {
    void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException;

    boolean isUrlPath(HttpRequest httpRequest);
}
