package webserver.middleware;

import java.io.IOException;

import http.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.FileIoUtils;

public class NotFound extends Middleware {
    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        try {
            byte[] file = FileIoUtils.loadFileFromClasspath("./templates/error/not_found.html");
            response.status(HttpStatus.NOT_FOUND)
                .set("Content-Type", "text/html;charset=utf-8")
                .end(file);
        } catch (Exception ignored) {
            next(request, response);
        }
    }
}
