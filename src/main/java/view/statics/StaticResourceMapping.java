package view.statics;

import webserver.http.MimeType;
import webserver.http.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

public class StaticResourceMapping {
    static final String DEFAULT_HTML_LOCATION = "./templates";
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

    public boolean isMapping(final String path) {
        return contains(parseToExtension(path));
    }

    private String parseToExtension(final String path) {
        return path.substring(path.lastIndexOf(".") + 1);
    }

    public boolean contains(final String extension) {
        return resources.containsKey(extension);
    }

    public void setAllLocations(final String location) {
        resources.entrySet()
                .stream()
                .filter(entry -> !(entry.getKey().equals(HTM) || entry.getKey().equals(HTML)))
                .forEach(entry -> entry.getValue().changeLocation(location));
    }

    public String getLocation(final String extension) {
        final StaticResource staticResource = resources.get(extension);
        return staticResource.getLocation();
    }

    public String addPrefix(final String viewName) {
        final StaticResource staticResource = resources.get(HttpUtils.parseExtension(viewName));
        return staticResource.getLocation() + viewName;
    }
}
