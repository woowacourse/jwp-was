package webserver;

import model.User;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private Map<String, Object> models;
    private String viewName;

    public ModelAndView() {
        this.models = new HashMap<>();
    }

    public void addModel(String key, Object model) {
        models.put(key, model);
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public Map<String, Object> getModels() {
        return Collections.unmodifiableMap(models);
    }
}
