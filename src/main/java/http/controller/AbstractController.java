package http.controller;

import http.exceptions.IllegalRequestMappingException;
import http.model.HttpRequest;
import http.supoort.RequestMapping;

import java.util.*;

public abstract class AbstractController implements Controller {
    private final Set<RequestMapping> mappings = new HashSet<>();

    public AbstractController(RequestMapping mapping) {
        this.mappings.add(mapping);
    }

    public AbstractController(RequestMapping... mappings) {
        Collection<RequestMapping> mappingCollection = Arrays.asList(mappings);
        if (mappingCollection.isEmpty()) {
            throw new IllegalRequestMappingException("There is No Mappings");
        }
        if (mappings.length != new HashSet<>(mappingCollection).size()) {
            throw new IllegalRequestMappingException("Ambiguous RequestMapping");
        }
        if (this.mappings.containsAll(mappingCollection)) {
            throw new IllegalRequestMappingException("Ambiguous RequestMapping");
        }
        this.mappings.addAll(mappingCollection);
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
