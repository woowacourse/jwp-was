package http.model;

import http.controller.NotFoundException;

import java.util.Objects;
import java.util.regex.Pattern;

public class HttpUri {
    private String resourceLocation;

    public HttpUri(String resourceLocation) {
        validate(resourceLocation);
        this.resourceLocation = resourceLocation;
    }

    private void validate(String resourceLocation) {
        if (!resourceLocation.startsWith("/")) {
            throw new NotFoundException();
        }
    }

    public String getResourceLocation() {
        return resourceLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpUri httpUri = (HttpUri) o;
        return Objects.equals(resourceLocation, httpUri.resourceLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourceLocation);
    }

    public boolean match(Pattern pattern) {
        return pattern.matcher(resourceLocation).find();
    }
}
