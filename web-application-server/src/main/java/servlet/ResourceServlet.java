package servlet;

import java.io.IOException;
import java.net.URISyntaxException;

import http.ContentType;
import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.FileIoUtils;

public class ResourceServlet implements HttpServlet {
    private static final String ROOT_PATH = "/";
    private static final String INDEX_PATH = "/index.html";

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            String path = ROOT_PATH.equals(httpRequest.getURI()) ? INDEX_PATH : httpRequest.getURI();
            byte[] body = FileIoUtils.loadFileFromClasspath(path);
            ContentType contentType = ContentType.findByURI(path);
            httpResponse.setBody(body, contentType);
        } catch (IOException | URISyntaxException e) {
            httpResponse.notFound();
        }
    }
}