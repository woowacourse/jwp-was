package webserver.servlet;

import db.DataBase;
import http.request.HttpRequest;
import http.response.ContentType;
import http.response.HttpResponse;
import model.User;
import utils.StringUtils;

public class UserCreateServlet implements Servlet {
    @Override
    public void doService(HttpRequest request, HttpResponse response) {
        createUser(request);
        response.responseRedirect(request, "/index.html", ContentType.HTML.getContentType());
    }

    private void createUser(HttpRequest request) {
        if (request.isPost()) {
            DataBase.addUser(User.from(StringUtils.readParameters(request.getBody())));
            return;
        }
        DataBase.addUser(User.from(request.getParameters()));
    }
}
