package webserver.response;

import java.util.Map;

public class ModelAndView {
    private final Model model;
    private final View view;

    public ModelAndView(Model model, String viewName) {
        this.model = model;
        this.view = View.of(viewName);
    }

    public static ModelAndView of(Map<String, String> body, String viewName) {
        Model model = new Model(body);

        return new ModelAndView(model, viewName);
    }

    public static ModelAndView of(String viewName) {
        return new ModelAndView(Model.emptyModel(), viewName);
    }

    public static ModelAndView of(StatusCode statusCode) {
        int statusCodeNum = statusCode.getStatusCode();

        if (statusCodeNum < 400) {
            throw new IllegalArgumentException();
        }

        if (statusCodeNum < 500) {
            return new ModelAndView(Model.emptyModel(), "static/notFound.html");
        }
        return new ModelAndView(Model.emptyModel(), "static/internalServerError.html");
    }

    public void put(String key, String value) {
        model.addBody(key, value);
    }

    public Model getModel() {
        return model;
    }

    public View getView() {
        return view;
    }

    public String getAttribute(String key) {
        return model.getAttribute(key);
    }
}
