package was.webserver;

import was.webserver.controller.Controller;
import was.webserver.http.request.HttpRequest;
import was.webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class HandlerMapper {
    private final List<Controller> controllers;

    public HandlerMapper(List<Controller> controllers) {
        this.controllers = controllers;
    }

    public void handle(HttpRequest httpRequest, HttpResponse httpResponse)
            throws IOException, URISyntaxException {
        Controller controller = controllers.stream()
                .filter(c -> c.isUrlPath(httpRequest))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        controller.service(httpRequest, httpResponse);
    }
}
