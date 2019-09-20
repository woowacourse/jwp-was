package http.controller;

import http.model.HttpRequest;
import http.supoort.NotSupportedRequestException;
import http.view.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HttpRequestHandlers {
    private static final int MIN_MAPPER_COUNT = 1;
    private List<Controller> controllers;

    public HttpRequestHandlers() {
        controllers = new ArrayList<>();
    }

    public void addHandler(Controller handler) {
        controllers.add(handler);
    }

    public ModelAndView doService(HttpRequest httpRequest) {
        return resolveRequestMapping(getCandidate(httpRequest)).handle(httpRequest);
    }

    private List<Controller> getCandidate(HttpRequest httpRequest) {
        return controllers.stream()
                .filter(controller -> controller.canHandle(httpRequest))
                .sorted()
                .collect(Collectors.toList());
    }

    private Controller resolveRequestMapping(List<Controller> requestMappings) {
        if (requestMappings.size() < MIN_MAPPER_COUNT) {
            throw new NotSupportedRequestException();
        }
        return requestMappings.get(0);
    }
}
