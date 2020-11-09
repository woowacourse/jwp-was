package web.view;

import web.model.Model;
import web.request.HttpRequest;
import web.response.HttpResponse;

public interface View {

    void render(Model model, HttpRequest httpRequest, HttpResponse httpResponse);
}

