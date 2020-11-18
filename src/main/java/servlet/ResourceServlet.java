package servlet;

import java.io.IOException;
import java.net.URISyntaxException;

import http.ContentType;
import http.HttpRequest;
import http.HttpResponse;
import utils.FileIoUtils;

public class ResourceServlet implements HttpServlet {
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(httpRequest.getURI());
            ContentType contentType = ContentType.findByURI(httpRequest.getURI());
            httpResponse.setBody(body, contentType);
        } catch (IOException | URISyntaxException e) {
            httpResponse.notFound();
        }
    }
}