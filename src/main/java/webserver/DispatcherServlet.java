package webserver;

import http.HttpRequest;
import http.HttpResponse;
import http.ResourceType;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class DispatcherServlet {
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";

    private final HandlerMapping handlerMapping;

    public DispatcherServlet(HandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    public void process(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        if (request.isStaticFileRequest()) {
            handleStaticFileRequest(request, response);
        } else {
            Servlet servlet = handlerMapping.findServlet(request.getPath());
            switch (request.getMethod()) {
                case GET:
                    servlet.doGet(request, response);
                case POST:
                    servlet.doPost(request, response);
                case PUT:
                    servlet.doPut(request, response);
                case DELETE:
                    servlet.doDelete(request, response);
            }
        }
    }

    private void handleStaticFileRequest(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        String path = httpRequest.getPath();
        ResourceType resourceType = ResourceType.from(path);
        String filePath = resourceType.getBaseDirectory() + path;

        byte[] staticFile = FileIoUtils.loadFileFromClasspath(filePath);

        httpResponse.response200Header(resourceType.getContentType(), staticFile.length);
        httpResponse.responseBody(staticFile);
    }
}
