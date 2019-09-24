package view;

import http.HttpRequest;
import http.HttpResponse;
import webserver.TemplatesFileLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView implements View {
    private String viewName;
    private Map<String, Object> model = new HashMap<>();

    public ModelAndView(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public void render(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        httpResponse.setStatus(200);
        httpResponse.setBody(TemplatesFileLoader.load(viewName, model).getBytes());
    }

    @Override
    public boolean isRedirectView() {
        return false;
    }

    @Override
    public String getViewName() {
        return viewName;
    }

    public void addAttribute(String attributeName, Object attributeValue) {
        model.put(attributeName, attributeValue);
    }
}