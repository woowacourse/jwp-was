package webserver;

import application.web.UserServlet;
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

    public DispatcherServlet(HttpRequest request, HttpResponse response) {
        this.request = request;
        this.response = response;
    }

    public void process() throws IOException, URISyntaxException {
        if (request.isStaticFileRequest()) {
            handleStaticFileRequest(request, response);
        } else {
            handleAPIRequest(request, response);
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

    private void handleAPIRequest(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (HTTP_METHOD_POST.equals(httpRequest.getMethod()) && "/user/create".equals(httpRequest.getPath())) {
            UserServlet userServlet = new UserServlet();
            userServlet.doPost(request);
            httpResponse.response302Header("/index.html");
        }
    }
}
