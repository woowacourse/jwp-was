package webserver;

import http.HttpRequest;
import http.HttpResponse;
import http.ResourceType;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class DispatcherServlet {
    public static final String HTTP_METHOD_POST = "POST";
    public static final String HTTP_METHOD_GET = "GET";
    public static final String HTTP_METHOD_PUT = "PUT";
    public static final String HTTP_METHOD_DELETE = "DELETE";

    private final HttpRequest request;
    private final HttpResponse response;
    private final HandlerMapping handlerMapping;

    public DispatcherServlet(HttpRequest request, HttpResponse response, HandlerMapping handlerMapping) {
        this.request = request;
        this.response = response;
        this.handlerMapping = handlerMapping;
    }

    public void process() throws IOException, URISyntaxException {
        if (request.isStaticFileRequest()) {
            handleStaticFileRequest(request, response);
        } else {
            Servlet servlet = handlerMapping.findServlet(request.getPath());
            switch (request.getMethod()) {
                case HTTP_METHOD_GET:
                    servlet.doGet(request, response);
                case HTTP_METHOD_POST:
                    servlet.doPost(request, response);
                case HTTP_METHOD_PUT:
                    servlet.doPut(request, response);
                case HTTP_METHOD_DELETE:
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
