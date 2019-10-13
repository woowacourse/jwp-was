package webserver.view;

import webserver.message.response.Response;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private View view;
    private Map<String, Object> models;

    private ModelAndView() {
    }

    public ModelAndView(final View view) {
        this(view, new HashMap<>());
    }

    public ModelAndView(final View view, final Map<String, Object> models) {
        this.view = view;
        this.models = models;
    }

    public View getView() {
        return view;
    }

    public Map<String, Object> getModels() {
        return Collections.unmodifiableMap(models);
    }

    public void addModel(final String key, final Object value) {
        this.models.put(key, value);
    }

    public void render(final Response response) {
        this.view.render(response, this.models);
    }
}
