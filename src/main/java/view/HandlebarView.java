package view;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpResponseBody;
import model.User;
import utils.HandlebarsHelper;

import java.util.Collection;
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

            //todo get은 제거가 불가능..?
            Collection<User> users = (Collection<User>) model.get("userList");
            response.setHttpResponseBody(new HttpResponseBody(HandlebarsHelper.apply(users)));
        }
    }
}
