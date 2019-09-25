package view;

import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;

public interface View {
    boolean isRedirectView();

    String getViewName();

    void render(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}