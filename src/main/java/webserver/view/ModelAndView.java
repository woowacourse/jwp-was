package webserver.view;

import java.util.Map;

public class ModelAndView {
    View view;
    Map<String, Object> modelMap;

    public ModelAndView(View view) {
        this.view = view;
    }

    public ModelAndView(View view, Map<String, Object> modelMap) {
        this.view = view;
        this.modelMap = modelMap;
    }

    public View getView() {
        return view;
    }

    public Map<String, Object> getModelMap() {
        return modelMap;
    }
}
