package webserver;

import http.HttpMimeType;
import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class ViewResolver {
    public static void resolve(HttpRequest request, HttpResponse response, String viewPath)
            throws IOException, URISyntaxException {
        if (viewPath != null) {
            byte[] body = FileIoUtils.loadFileFromClasspath(viewPath);
            HttpMimeType type = request.getMimeType();
            response.setBody(body, type);
        }
    }
}
