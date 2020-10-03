package webserver.controller;

import utils.FileIoUtils;
import webserver.domain.request.HttpRequest;
import webserver.domain.response.HttpResponse;

public class StaticController extends AbstractController {
    @Override
    protected HttpResponse get(HttpRequest httpRequest) {
        String defaultPath = httpRequest.getDefaultPath();
        String path = defaultPath;
        if (isTemplatesResource(defaultPath)) {
            path = String.format("./templates%s", defaultPath);
        }

        if (isStaticResource(defaultPath)) {
            path = String.format("./static%s", defaultPath);
        }

        byte[] body = FileIoUtils.loadFileFromClasspath(path);
        String contentType = "";
        if (path.endsWith(".html")) {
            contentType = "text/html;charset=utf-8";
        } else if (path.endsWith(".css")) {
            contentType = "text/css";
        } else if (path.endsWith(".js")) {
            contentType = "application/javascript";
        } else {
            contentType = "text/plain";
        }
        return HttpResponse.ok()
            .contentType(contentType)
            .contentLength(body.length)
            .body(body)
            .build();
    }

    public boolean isForStaticContent(HttpRequest httpRequest) {
        String path = httpRequest.getDefaultPath();
        return isTemplatesResource(path) | isStaticResource(path);
    }

    private boolean isTemplatesResource(String path) {
        return path.endsWith("html") || path.equals("/favicon.ico");
    }

    private boolean isStaticResource(String path) {
        return !isTemplatesResource(path) && path.contains(".");
    }
}
