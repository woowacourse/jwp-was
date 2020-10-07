package view;

public class ModelAndView {

    private final Model model;
    private final View view;

    private ModelAndView(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public static ModelAndView of(Model model, View view) {
        return new ModelAndView(model, view);
    }

    public static ModelAndView from(View view) {
        return new ModelAndView(null, view);
    }

    public Model getModel() {
        return model;
    }

    public View getView() {
        return view;
    }
}
