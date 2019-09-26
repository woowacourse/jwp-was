package servlet;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponseEntity;
import model.User;

public class UserServlet extends AbstractServlet {
    @Override
    protected HttpResponseEntity doPost(HttpRequest httpRequest) {
        User user = new User(httpRequest.getParam("userId"), httpRequest.getParam("password"),
                httpRequest.getParam("name"), httpRequest.getParam("email"));
        DataBase.addUser(user);
        return HttpResponseEntity.get302Response("/index.html");
    }
}
