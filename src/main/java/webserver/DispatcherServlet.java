package webserver;

import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import http.ResourceType;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class DispatcherServlet {
    private final HandlerMapping handlerMapping;

    public DispatcherServlet(HandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    public void process(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        if (request.isStaticFileRequest()) {
            handleStaticFileRequest(request, response);
        } else {
            Servlet servlet = handlerMapping.findServlet(request.getPath());
            servlet.service(request, response);
        }
    }

    private void handleStaticFileRequest(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        String path = httpRequest.getPath();
        ResourceType resourceType = ResourceType.from(path);
        String filePath = resourceType.getBaseDirectory() + path;

        byte[] staticFile = FileIoUtils.loadFileFromClasspath(filePath);

        httpResponse.response(HttpStatus.OK, resourceType, staticFile);
    }
}
