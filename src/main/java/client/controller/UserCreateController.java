package client.controller;

import db.DataBase;
import model.User;
import web.controller.Controller;
import web.request.HttpRequest;
import web.request.RequestBody;
import web.response.HttpResponse;

public class UserCreateController implements Controller {
    @Override
    public void doService(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        RequestBody requestBody = httpRequest.getRequestBody();
        createUser(requestBody);

        httpResponse.sendRedirect("/index.html");
    }

    private void createUser(RequestBody requestBody) {
        User user = new User(requestBody.getParameter("userId"),
                requestBody.getParameter("password"),
                requestBody.getParameter("name"),
                requestBody.getParameter("email"));
        DataBase.addUser(user);
    }
}
