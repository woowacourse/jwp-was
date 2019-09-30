package servlet;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import view.HandlebarsView;
import view.View;

import java.util.HashMap;
import java.util.Map;

public class UserListServlet extends AbstractServlet {
    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        if (request.matchCookie("logined", "true")) {
            Map<String, Object> model = new HashMap<>();
            model.put("users", DataBase.findAll());
            View view = new HandlebarsView("/user/list", model);
            response.sendResponseWithView(view, request.getMimeType());
            return;
        }
        response.sendRedirect("/index.html");
    }
}
