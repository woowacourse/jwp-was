package webserver;

import controller.Controller;
import http.HttpMethod;
import http.HttpRequest;
import http.StaticResourceType;
import utils.FileIoUtils;

public class ResourceHandlerMapping implements HandlerMapping {
    @Override
    public boolean matches(final HttpRequest httpRequest) {
        return httpRequest.equalsMethod(HttpMethod.GET) && StaticResourceType.anyMatch(httpRequest.getUri());
    }

    @Override
    public Controller getController() {
        return (httpRequest, httpResponse) -> {
            StaticResourceType resourceType = StaticResourceType.findByUri(httpRequest.getUri());
            byte[] body = FileIoUtils.loadFileFromClasspath(resourceType.getResourceLocation() + httpRequest.getPath());
            httpResponse.response200Header(resourceType.getContentType(), body.length);
            httpResponse.ok(body);
        };
    }
}
