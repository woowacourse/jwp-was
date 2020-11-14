package webserver;

import webserver.controller.Controller;
import webserver.controller.IndexController;
import webserver.controller.TemplateController;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class HandlerMapper {
    private static final List<Controller> controllers;

    static {
        controllers = new ArrayList<>();
        controllers.add(new IndexController());
        controllers.add(new TemplateController());
    }

    public static void handle(HttpRequest httpRequest, HttpResponse httpResponse)
            throws IOException, URISyntaxException {
        Controller controller = controllers.stream()
                .filter(c -> c.isUrlPath(httpRequest))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        controller.service(httpRequest, httpResponse);
    }
}
