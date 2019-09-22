package webserver.http.handler;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class StaticResourceHandler implements ResourceHandler {
    private StaticResourceMapping staticResourceMapping;

    public StaticResourceHandler(final StaticResourceMapping staticResourceMapping) {
        this.staticResourceMapping = staticResourceMapping;
    }

    @Override
    public void handle(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        final String path = httpRequest.getPath();
        final String extension = parseToExtension(path);
        final String pathOfResource = staticResourceMapping.getLocation(extension) + path;

        httpResponse.forward(pathOfResource);
    }

    @Override
    public boolean isMapping(final String path) {
        final String extension = parseToExtension(path);
        return staticResourceMapping.contains(extension);
    }

    private String parseToExtension(final String path) {
        return path.substring(path.lastIndexOf(".") + 1);
    }

}
