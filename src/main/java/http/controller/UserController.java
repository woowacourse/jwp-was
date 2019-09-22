package http.controller;

import http.model.ServletRequest;
import http.model.ServletResponse;
import http.supoort.RequestMapping;

public class UserController extends AbstractController {
    public UserController(RequestMapping mapping) {
        super(mapping);
    }

    public UserController(RequestMapping... mappings) {
        super(mappings);
    }

    @Override
    public void handle(ServletRequest servletRequest, ServletResponse servletResponse) {

    }
}
