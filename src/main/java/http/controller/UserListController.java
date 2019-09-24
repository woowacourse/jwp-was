package http.controller;

import db.DataBase;
import http.model.request.ServletRequest;
import http.model.response.ServletResponse;
import http.supoort.RequestMapping;
import model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserListController extends AbstractController {
    public UserListController(RequestMapping mapping) {
        super(mapping);
    }

    public UserListController(RequestMapping... mappings) {
        super(mappings);
    }

    @Override
    public void handle(ServletRequest servletRequest, ServletResponse servletResponse) {
        if (!servletRequest.hasCookie() || !"true".equals(servletRequest.getCookie("logined"))) {
            servletResponse.redirect("/index.html");
            return;
        }

        Collection<User> all = DataBase.findAll();

        Map<String, Object> model = new HashMap<String, Object>() {{
            put("users", all);
        }};

        servletResponse.ok("/user/list");
        servletResponse.setModel(model);
    }
}
