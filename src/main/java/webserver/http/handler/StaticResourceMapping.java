package webserver.http.handler;

import webserver.http.MimeType;

import java.util.HashMap;
import java.util.Map;

public class StaticResourceMapping {
    protected static final String DEFAULT_HTML_LOCATION = "./templates";
    private static final String HTM = "htm";
    private static final String HTML = "html";

    private final Map<String, StaticResource> resources;

    public StaticResourceMapping() {
        resources = new HashMap<>();

        for (final String extension : MimeType.getExtensions()) {
            resources.put(extension, StaticResource.of(extension));
        }
        resources.put(HTM, StaticResource.of(HTM, DEFAULT_HTML_LOCATION));
        resources.put(HTML, StaticResource.of(HTML, DEFAULT_HTML_LOCATION));
    }

    public void addStaticResource(final String extension, final String location) {
        resources.put(extension, StaticResource.of(extension, location));
    }

    public boolean contains(final String extension) {
        return resources.containsKey(extension);
    }

    public void setAllLocations(final String location) {
        for (final String key : resources.keySet()) {
            if (!(key.equals(HTM) || key.equals(HTML))) {
                resources.get(key).changeLocation(location);
            }
        }
    }

    public String getLocation(final String extension) {
        final StaticResource staticResource = resources.get(extension);
        return staticResource.getLocation();
    }
}
