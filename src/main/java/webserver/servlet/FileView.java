package webserver.servlet;

import webserver.http.request.HttpRequest;
import webserver.http.request.RequestURI;
import webserver.http.response.HttpResponse;

public class FileView implements View {
    private final RequestURI uri;

    public FileView(RequestURI uri) {
        this.uri = uri;
    }

    @Override
    public void render(HttpRequest request, HttpResponse response) {
        response.ok(uri.getClassPath(), uri.getContentType());
    }
}
