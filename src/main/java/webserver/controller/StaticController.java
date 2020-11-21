package webserver.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

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

        return HttpResponse.ok()
            .bodyByPath(path)
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
