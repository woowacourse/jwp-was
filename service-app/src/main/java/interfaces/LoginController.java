package interfaces;

import db.DataBase;
import model.User;
import web.session.HttpSession;
import web.controller.AbstractController;
import web.http.HttpRequest;
import web.http.HttpResponse;

public class LoginController extends AbstractController {
    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        try {
            User user = validatedUser(request);
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect("/index.html");
        } catch (IllegalArgumentException e) {
            response.sendRedirect("/user/login_failed.html");
        }
    }

    private User validatedUser(HttpRequest request) {
        User user = DataBase.findUserById(
            request.getRequestBody().getParameter("userId")
        ).orElseThrow(IllegalArgumentException::new);

        if (user.notAuthenticated(request.getRequestBody().getParameter("password"))) {
            throw new IllegalArgumentException();
        }

        return user;
    }
}
