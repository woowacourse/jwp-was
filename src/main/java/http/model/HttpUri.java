package http.model;

import http.controller.NotFoundException;

import java.util.Objects;
import java.util.regex.Pattern;

public class HttpUri {
    private static final String QUERY_STRING_INDICATOR = "?";
    private String resourceLocation;

    public HttpUri(String uri) {
        validate(uri);

        if (uri.contains(QUERY_STRING_INDICATOR)) {
            getPureHttpUri(uri);
            return;
        }
        resourceLocation = uri;
    }

    private void getPureHttpUri(String uri) {
        resourceLocation = uri.substring(0, uri.indexOf(QUERY_STRING_INDICATOR));
    }

    private void validate(String resourceLocation) {
        if (!resourceLocation.startsWith("/")) {
            throw new NotFoundException();
        }
    }

    public String getResourceLocation() {
        return resourceLocation;
    }

    public boolean match(Pattern pattern) {
        return pattern.matcher(resourceLocation).find();
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
}
