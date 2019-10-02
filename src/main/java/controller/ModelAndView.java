package controller;

import java.util.Collections;
import java.util.Map;

public class ModelAndView {
    private final String viewName;
    private final Map<String, Object> modelMap;

    public ModelAndView(String viewName) {
        this.viewName = viewName;
        modelMap = Collections.emptyMap();
    }

    public ModelAndView(String viewName, Map<String, Object> modelMap) {
        this.viewName = viewName;
        this.modelMap = modelMap;
    }

    public String getViewName() {
        return viewName;
    }

    public Map<String, Object> getModelMap() {
        return modelMap;
    }
}
