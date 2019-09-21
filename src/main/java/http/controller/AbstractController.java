package http.controller;

import http.exceptions.IllegalRequestMappingException;
import http.model.HttpRequest;
import http.supoort.RequestMapping;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractController implements Controller {
    private final Set<RequestMapping> mappings = new HashSet<>();

    public AbstractController(RequestMapping mapping) {
        this.mappings.add(mapping);
    }

    public AbstractController(RequestMapping... mappings) {
        if (mappings.length != new HashSet<>(Arrays.asList(mappings)).size()) {
            throw new IllegalRequestMappingException("Ambiguous RequestMapping");
        }
        if (this.mappings.containsAll(Arrays.asList(mappings))) {
            throw new IllegalRequestMappingException("Ambiguous RequestMapping");
        }
        this.mappings.addAll(Arrays.asList(mappings));
    }

    @Override
    public boolean canHandle(HttpRequest httpRequest) {
        return mappings.stream()
                .anyMatch(mapping -> mapping.match(httpRequest));
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
