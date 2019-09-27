package http;

import java.util.Collection;
import java.util.Map;

public class ModelAndView {
    private View view;
    private ModelMap model;
    private HandlebarView handlebarView = new HandlebarView();

    public ModelAndView() {
        this.view = new View();
        this.model = new ModelMap();
    }

    public void addAllObjects(Map<String, Collection> modelMap) {
        model.addAllAttribute(modelMap);
    }

    public void setView(String viewName) {
        view.setView(viewName);
    }

    public String render() {
        return handlebarView.render(view.getView(), model.getModels());
    }
}