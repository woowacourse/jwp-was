package webserver.Controller;

import db.DataBase;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.util.Map;

public class SignUpController implements Controller {

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.isGet()) {
            doGet(httpRequest, httpResponse);
        }
    }

    private void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.hasParameters()) {
            Map<String, String> parameters = httpRequest.getParameters();
            User user = new User(parameters.get("userId"), parameters.get("password"), parameters.get("name"), parameters.get("email"));
            DataBase.addUser(user);
            httpResponse.addHeader(httpRequest, "200", "OK");
        }
    }
}
