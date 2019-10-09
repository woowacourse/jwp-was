package webserver.resolver;

import java.util.Set;

class ResourceResolverRegistration {
    private Set<String> extensionSet;
    private String resourcePath;

    ResourceResolverRegistration(Set<String> extensionSet, String resourcePath) {
        this.extensionSet = extensionSet;
        this.resourcePath = resourcePath;
    }

    boolean existExtension(String contentType) {
        return extensionSet.contains(contentType);
    }

    String findFilePath(String path) {
        return resourcePath + path;
    }
}
