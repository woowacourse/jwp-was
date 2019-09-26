package controller;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {

    private Map<String, Object> objects;
    private String view;

    public ModelAndView() {
        objects = new HashMap<>();
    }

    public void put(String key, Object value) {
        objects.put(key, value);
    }

    public void addView(String view) {
        this.view = view;
    }

    public Map<String, Object> getObjects() {
        return objects;
    }

    public String getView() {
        return view;
    }
}
