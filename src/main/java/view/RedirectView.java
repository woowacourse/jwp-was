package view;

import http.HttpRequest;
import http.HttpResponse;

public class RedirectView implements View {
    private String viewName;

    public RedirectView(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public boolean isRedirectView() {
        return true;
    }

    @Override
    public String getViewName() {
        return viewName;
    }

    @Override
    public void render(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.setStatus(302);
        httpResponse.addHeader("Location", "/" + viewName);
    }
}