package webserver.middleware;

import java.io.IOException;

import http.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.FileIoUtils;
import utils.MimeType;

public class ServeStatic extends Middleware {
    private final String root;

    public ServeStatic(String root) {
        this.root = root;
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        try {
            byte[] file = FileIoUtils.loadFileFromClasspath("./" + root + request.path());

            response.status(HttpStatus.OK)
                .set("Content-Type", MimeType.from(request.path()).getMimeType() + ";charset=utf-8")
                .end(file);
        } catch (Exception ignored) {
            next(request, response);
        }
    }
}
