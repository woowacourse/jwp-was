package http.controller;

import http.exceptions.IllegalRequestMappingException;
import http.model.request.ServletRequest;
import http.supoort.RequestMapping;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class RequestMappings {
    private final Set<RequestMapping> mappings;

    public RequestMappings(RequestMapping mapping) {
        this.mappings = new HashSet<>();
        this.mappings.add(mapping);
    }

    public RequestMappings(RequestMapping... mappingArgs) {
        mappings = Arrays.stream(mappingArgs).collect(Collectors.toSet());
        if (mappings.isEmpty()) {
            throw new IllegalRequestMappingException("There is No Mappings");
        }
        if (mappings.size() != mappingArgs.length) {
            throw new IllegalRequestMappingException("Ambiguous RequestMapping");
        }
    }

    public boolean hasMapping(ServletRequest request) {
        return mappings.stream()
                .anyMatch(mapping -> mapping.match(request));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestMappings that = (RequestMappings) o;
        return Objects.equals(mappings, that.mappings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mappings);
    }
}
