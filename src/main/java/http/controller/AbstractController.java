package http.controller;

import http.model.HttpRequest;
import http.supoort.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractController implements Controller {
    private final List<RequestMapping> mappings = new ArrayList<>();

    public AbstractController(RequestMapping mapping) {
        this.mappings.add(mapping);
    }

    public AbstractController(RequestMapping... mappings) {
        this.mappings.addAll(Arrays.asList(mappings));
    }

    @Override
    public boolean canHandle(HttpRequest httpRequest) {
        return mappings.stream()
                .anyMatch(mapping -> mapping.match(httpRequest));
    }
}
