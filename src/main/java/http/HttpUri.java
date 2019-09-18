package http;

import java.util.Objects;

public class HttpUri {
    private String resourceLocation;

    public HttpUri(String resourceLocation) {
        this.resourceLocation = resourceLocation;
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
}
