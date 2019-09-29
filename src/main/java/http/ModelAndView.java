package http;

import java.util.Collection;
import java.util.Map;

public class ModelAndView {
    private View view;
    private ModelMap model;

    public ModelAndView(View view) {
        this.model = new ModelMap();
        this.view = view;
    }

    public void addAllObjects(Map<String, Collection> modelMap) {
        model.addAllAttribute(modelMap);
    }

    public String render() {
        return view.render(model);
    }

    public String getViewName() {
        return view.getViewName();
    }
}