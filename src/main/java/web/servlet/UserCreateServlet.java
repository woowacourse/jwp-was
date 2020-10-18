package web.servlet;

import db.DataBase;
import model.User;
import web.HttpRequest;
import web.HttpResponse;
import web.RequestBody;

public class UserCreateServlet implements Servlet {
    @Override
    public void doService(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        RequestBody requestBody = httpRequest.getRequestBody();
        createUser(requestBody);
        httpResponse.response302("/index.html");
    }

    private void createUser(RequestBody requestBody) {
        User user = new User(requestBody.getParameter("userId"),
                requestBody.getParameter("password"),
                requestBody.getParameter("name"),
                requestBody.getParameter("email"));
        DataBase.addUser(user);
    }
}
