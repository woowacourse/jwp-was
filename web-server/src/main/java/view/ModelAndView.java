package view;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private final View view;
    private final Map<String, Object> models;

    public ModelAndView(View view) {
        this.view = view;
        this.models = new HashMap<>();
    }

    public ModelAndView(View view, Map<String, Object> models) {
        this.view = view;
        this.models = models;
    }

    public String render() throws IOException {
        return view.apply(models);
    }
}
