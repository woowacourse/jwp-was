package http.view;

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

    public String getViewLocation() {
        return this.view.getResourceLocation();
    }

    public Model getModel() {
        return model;
    }
}
