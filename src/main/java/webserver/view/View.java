package webserver.view;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface View {
    void render(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException;
}
