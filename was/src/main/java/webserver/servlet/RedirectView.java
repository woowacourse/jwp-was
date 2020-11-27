package webserver.servlet;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class RedirectView extends AbstractView {
    private final String redirectUri;

    public RedirectView(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    @Override
    void render(HttpRequest request, HttpResponse response) {
        response.redirect(redirectUri);
    }
}
