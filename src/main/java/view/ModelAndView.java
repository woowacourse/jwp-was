package view;

import http.request.HttpRequest;
import http.response.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private View view;
    private Map<String, Object> model;

    public ModelAndView(View view) {
        this.view = view;
        model = new HashMap<>();
    }

    public ModelAndView(View view, Map<String, Object> model) {
        this.view = view;
        this.model = model;
    }

    public ModelAndView(View view, String modelName, Object modelObject) {
        this.view = view;
        model = new HashMap<>();
        model.put(modelName, modelObject);
    }

    public void addObject(String attributeName, Object attributeValue) {
        model.put(attributeName, attributeValue);
    }

    public void render(HttpRequest httpRequest, HttpResponse httpResponse) {
        view.render(model, httpRequest, httpResponse);
    }

    public View getView() {
        return view;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
