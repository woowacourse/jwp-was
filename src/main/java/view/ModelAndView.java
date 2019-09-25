package view;

import http.HttpRequest;
import http.HttpResponse;
import utils.FileIoUtils;
import webserver.TemplatesFileLoader;
import webserver.exception.NotFoundResourceException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView implements View {
    private String viewName;
    private Map<String, Object> model = new HashMap<>();

    public ModelAndView(String viewName) {
        this.viewName = viewName;
    }

    public void addAttribute(String attributeName, Object attributeValue) {
        model.put(attributeName, attributeValue);
    }

    @Override
    public boolean isRedirectView() {
        return false;
    }

    @Override
    public String getViewName() {
        return viewName;
    }

    @Override
    public void render(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        validateFileExist();
        httpResponse.setStatus(200);
        httpResponse.setBody(TemplatesFileLoader.load(viewName, model).getBytes());
    }

    private void validateFileExist() {
        String path = "./templates/" + viewName;
        if (!FileIoUtils.isExistFile(path)) {
            throw new NotFoundResourceException();
        }
    }
}