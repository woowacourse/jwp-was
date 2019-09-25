package http.controller;

import http.model.request.ServletRequest;
import http.supoort.RequestMapping;

import java.util.Objects;

public abstract class AbstractController implements Controller {
    private final RequestMappings mappings;

    public AbstractController(RequestMapping mapping) {
        this.mappings = new RequestMappings(mapping);
    }

    public AbstractController(RequestMapping... mappings) {
        this.mappings = new RequestMappings(mappings);
    }

    @Override
    public boolean canHandle(ServletRequest request) {
        return mappings.hasMapping(request);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractController)) return false;
        AbstractController that = (AbstractController) o;
        return Objects.equals(mappings, that.mappings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mappings);
    }
}
