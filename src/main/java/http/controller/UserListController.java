package http.controller;

import http.model.request.ServletRequest;
import http.model.response.ServletResponse;
import http.supoort.RequestMapping;

public class UserListController extends AbstractController {
    public UserListController(RequestMapping mapping) {
        super(mapping);
    }

    public UserListController(RequestMapping... mappings) {
        super(mappings);
    }

    @Override
    public void handle(ServletRequest servletRequest, ServletResponse servletResponse) {
        if (!servletRequest.hasCookie() || !servletRequest.getCookie("logined").equals("true")) {
            servletResponse.redirect("/index.html");
            return;
        }

    }
}
