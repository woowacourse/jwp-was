package web.servlet;

import db.DataBase;
import model.User;
import web.HttpRequest;
import web.HttpResponse;
import web.RequestBody;

import java.util.Map;

public class UserCreateServlet implements Servlet {
    @Override
    public void doService(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        RequestBody requestBody = httpRequest.getRequestBody();
        Map<String, String> parsedBody = requestBody.parse();
        User user = new User(parsedBody.get("userId"),
                parsedBody.get("password"),
                parsedBody.get("name"),
                parsedBody.get("email"));
        DataBase.addUser(user);
        byte[] body = user.toString().getBytes();

        httpResponse.response302Header("/index.html");
        httpResponse.responseBody(body);
    }
}
