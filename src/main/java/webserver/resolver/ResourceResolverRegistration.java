package webserver.resolver;

import http.response.HttpResponse;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
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

    private String findFilePath(String path) {
        return resourcePath + path;
    }

    void resolve(String path, String contentType, HttpResponse httpResponse) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(findFilePath(path));
        httpResponse.okResponse(contentType, body);
    }
}
