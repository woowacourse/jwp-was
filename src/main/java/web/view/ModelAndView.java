package web.view;

import web.model.Model;
import web.request.HttpRequest;
import web.response.HttpResponse;

public class ModelAndView {
    private final Model model;
    private final View view;

    public static ModelAndView view(View view) {
        return new ModelAndView(new Model(), view);
    }

    public ModelAndView(Model model, View view) {
        this.model = model;
        this.view = view;
    }


    public void render(HttpRequest httpRequest, HttpResponse httpResponse) {
        //TODO: model이 비어잇으면?
        view.render(model, httpRequest, httpResponse);
    }
}
