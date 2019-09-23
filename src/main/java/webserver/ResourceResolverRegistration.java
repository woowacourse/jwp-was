package webserver;

import http.request.HttpRequest;

import java.util.Set;

public class ResourceResolverRegistration {
    private Set<String> extensionSet;
    private String resourcePath;

    public ResourceResolverRegistration(Set<String> extensionSet, String resourcePath) {
        this.extensionSet = extensionSet;
        this.resourcePath = resourcePath;
    }

    public String resolve(HttpRequest httpRequest) {
        if (!isTarget(httpRequest.getPath())) {
            throw new IllegalArgumentException("Not Supported Extension, " + httpRequest.getPath());
        }
        return resourcePath + httpRequest.getPath();
    }

    public boolean isTarget(String path) {
        return extensionSet.contains(FileUtils.getExtension(path));
    }
}
