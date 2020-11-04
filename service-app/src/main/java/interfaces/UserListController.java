package interfaces;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import db.DataBase;
import model.User;
import view.ModelAndView;
import view.View;
import web.StaticFile;
import web.controller.AbstractController;
import web.http.HttpRequest;
import web.http.HttpResponse;
import web.session.HttpSession;

public class UserListController extends AbstractController {
    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        if (!isLogin(request)) {
            response.sendRedirect("/user/login");
            return;
        }

        Collection<User> users = DataBase.findAll();
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        String location = "/user/list.html";
        View view = new View(StaticFile.of(location).getPrefix() + location);
        response.ok(new ModelAndView(view, model));
    }

    private boolean isLogin(HttpRequest request) {
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        return user != null;
    }
}
