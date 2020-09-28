package webserver;

import java.io.IOException;
import java.net.URISyntaxException;

import http.HttpRequest;
import http.HttpResponse;
import http.StaticResourceType;
import utils.FileIoUtils;

public class ResourceRequestHandler implements Handler {
    @Override
    public void handleRequest(final HttpRequest httpRequest, final HttpResponse httpResponse) throws
            IOException,
            URISyntaxException {
        StaticResourceType resourceType = StaticResourceType.findByUri(httpRequest.getUri());
        byte[] body = FileIoUtils.loadFileFromClasspath(resourceType.getResourceLocation() + httpRequest.getPath());
        httpResponse.response200Header(resourceType.getContentType(), body.length);
        httpResponse.responseBody(body);
    }
}
