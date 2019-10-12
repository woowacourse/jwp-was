package view;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpResponseBody;
import utils.HandlebarsHelper;

import java.util.Map;

public class HandlebarView implements View {
    private String path;

    public HandlebarView(String path) {
        this.path = path;
    }

    @Override
    public void render(Map<String, ?> model,  HttpRequest request, HttpResponse response) throws Exception {
        response.forward(request.getPath() + path);

        if (!model.isEmpty()) {
            response.setHttpResponseBody(new HttpResponseBody(HandlebarsHelper.apply(model)));
        }
    }
}
