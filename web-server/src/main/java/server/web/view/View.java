package server.web.view;

import server.web.model.Model;
import server.web.request.HttpRequest;
import server.web.response.HttpResponse;

public interface View {

    void render(Model model, HttpRequest httpRequest, HttpResponse httpResponse);
}

