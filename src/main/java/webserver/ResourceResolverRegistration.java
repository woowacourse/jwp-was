package webserver;

import http.request.ServerErrorException;
import http.response.HttpResponse;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public class ResourceResolverRegistration {
    private Set<String> extensionSet;
    private String resourcePath;

    public ResourceResolverRegistration(Set<String> extensionSet, String resourcePath) {
        this.extensionSet = extensionSet;
        this.resourcePath = resourcePath;
    }

    public boolean existExtension(String contentType) {
        return extensionSet.contains(contentType);
    }

    public String findFilePath(String path) {
        return resourcePath + path;
    }

    public void resolve(String path, String contentType, HttpResponse httpResponse) {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(findFilePath(path));
            httpResponse.okResponse(contentType, body);
        } catch (IOException e) {
            throw new NotFoundException();
        } catch (URISyntaxException e) {
            throw new ServerErrorException("URISyntax 에러 입니다.");
        }

    }
}
