package view;

import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;

public interface View {
    void render(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;

    String getViewName();

    boolean isRedirectView();
}