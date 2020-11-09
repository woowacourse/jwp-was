package web.view;

import web.model.Model;

public class ModelAndView {
    private final Model model;
    private final View view;

    public ModelAndView(Model model, View view) {
        this.model = model;
        this.view = view;
    }
}
