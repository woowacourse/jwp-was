package controller;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {

    private Map<String, Object> objects;
    private String view;

    ModelAndView() {
        objects = new HashMap<>();
    }

    void put(String key, Object value) {
        objects.put(key, value);
    }

    void addView(String view) {
        this.view = view;
    }

    Map<String, Object> getObjects() {
        return objects;
    }

    String getView() {
        return view;
    }
}
