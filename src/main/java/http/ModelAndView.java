package http;

import http.request.HttpRequest;
import http.response.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private View view;
    private Map<String, Object> model = new HashMap<>();

    public ModelAndView() {
    }

    public ModelAndView(View view) {
        this.view = view;
    }

    public ModelAndView(View view, Map<String, Object> model) {
        this.view = view;
        this.model = model;
    }

    public void addAttribute(String attributeName, Object attribute) {
        model.put(attributeName, attribute);
    }

    public void render(HttpRequest request, HttpResponse response) throws Exception {
        view.render(request, response);
    }
}
