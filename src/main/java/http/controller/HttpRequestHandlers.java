package http.controller;

import http.model.HttpRequest;
import http.supoort.NotSupportedRequestException;
import http.supoort.RequestMapping;
import http.view.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequestHandlers {
    private static final int MIN_MAPPER_COUNT = 1;
    private Map<RequestMapping, HttpRequestHandler> handlers;

    public HttpRequestHandlers() {
        handlers = new HashMap<>();
    }

    public void addHandler(RequestMapping mapping, HttpRequestHandler handler) {
        handlers.put(mapping, handler);
    }

    public ModelAndView doService(HttpRequest httpRequest) {
        return handlers.get(resolveRequestMapping(getCandidate(httpRequest))).handle(httpRequest);
    }

    private List<RequestMapping> getCandidate(HttpRequest httpRequest) {
        return handlers.keySet().stream()
                .filter(key -> key.match(httpRequest))
                .sorted()
                .collect(Collectors.toList());
    }

    private RequestMapping resolveRequestMapping(List<RequestMapping> requestMappings) {
        if (requestMappings.size() < MIN_MAPPER_COUNT) {
            throw new NotSupportedRequestException();
        }
        return requestMappings.get(0);
    }
}
