package controller;

import controller.controllermapper.ControllerMapper;
import db.DataBase;
import http.request.Request;
import http.request.RequestMethod;
import http.response.Response;
import http.session.Session;
import http.session.sessionkeygenerator.UUIDSessionKeyGenerator;
import model.User;

public class LoginController implements Controller {
    private RequestMapping requestMapping = new RequestMapping(RequestMethod.POST, "/user/login");

    @Override
    public boolean isMapping(ControllerMapper controllerMapper) {
        return requestMapping.isCorrectMapping(controllerMapper);
    }


    @Override
    public void processResponse(Request request, Response response) {
        User user = DataBase.findUserById(request.getQueryParameters().getParameter("userId"));

        if (user != null && user.isCorrectPassWord(request.getQueryParameters().getParameter("password"))) {
            Session session = request.getSession(new UUIDSessionKeyGenerator());
            session.setAttribute("user", user.getUserId());

            response.found()
                    .putResponseHeaders("Location: ", "http://localhost:8080/index.html")
                    .putResponseHeaders("Set-Cookie: Session-Id=", session.getSessionId() + "; Path=/");
        }

        if (user == null || !user.isCorrectPassWord(request.getQueryParameters().getParameter("password"))) {
            response.found()
                    .putResponseHeaders("Location: ", "http://localhost:8080/user/login_failed.html");
        }
    }
}
