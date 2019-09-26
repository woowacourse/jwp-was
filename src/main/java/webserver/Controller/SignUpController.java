package webserver.Controller;

import db.DataBase;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class SignUpController extends AbstractController {

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = new User(
                httpRequest.getParameter("userId"),
                httpRequest.getParameter("password"),
                httpRequest.getParameter("name"),
                httpRequest.getParameter("email"));
        DataBase.addUser(user);
        httpResponse.addStatusLine(httpRequest, "200", "OK");
        httpResponse.addHeader("Content-Type", "text/html;charset=utf-8");
    }

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = new User(
                httpRequest.getParameter("userId"),
                httpRequest.getParameter("password"),
                httpRequest.getParameter("name"),
                httpRequest.getParameter("email"));
        DataBase.addUser(user);
        httpResponse.addStatusLine(httpRequest, "302", "Found");
        httpResponse.addHeader("Location", httpRequest.getRequestHeader().get("Origin") + "/index.html");
    }
}
