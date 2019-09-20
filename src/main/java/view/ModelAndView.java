package view;

import http.HttpRequest;
import http.HttpResponse;

public class ModelAndView implements View {
    private String viewName;

    public ModelAndView(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public void render(HttpRequest httpRequest, HttpResponse httpResponse) {

    }

    @Override
    public String getViewName() {
        return viewName;
    }
}
