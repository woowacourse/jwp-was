package webserver;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpResponseEntity;
import http.response.HttpResponseFactory;
import servlet.Servlet;

import java.io.IOException;
import java.net.URISyntaxException;

public class ServletHandler {
    public static HttpResponse handle(HttpRequest request) throws URISyntaxException, IOException {
        String path = request.getUri().getPath();
        Servlet servlet = ServletMapper.getServlet(path);

        HttpResponseEntity responseEntity = servlet.handle(request);
        HttpResponse response = HttpResponseFactory.makeResponse(responseEntity);

        String viewPath = responseEntity.getViewPath();
        ViewResolver.resolve(request, response, viewPath);
        return response;
    }
}