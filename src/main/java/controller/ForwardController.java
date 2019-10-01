package controller;

import http.HttpRequest;
import http.HttpResponse;

public class ForwardController extends AbstractController {
    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {
        String path = getDefaultPath(request.getPath());
        response.forward(removeSuffix(path));
    }

    private String getDefaultPath(String path) {
        if (path.equals("/")) {
            return "/index.html";
        }
        return path;
    }

    private String removeSuffix(String viewName) {
        return viewName.replace(".html", "");
    }
}
