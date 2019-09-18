package http;

import java.nio.file.Path;

public class ModelAndView {
    private Model model;
    private View view;

    public ModelAndView(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public ModelAndView(Model model) {
        this.model = model;
    }

    public ModelAndView(View view) {
        this.view = view;
    }

    public Path getViewName() {
        return this.view.getPath();
    }
}
