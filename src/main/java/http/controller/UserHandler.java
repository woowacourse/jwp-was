package http.controller;

import http.model.ServletRequest;
import http.model.ServletResponse;
import http.supoort.RequestMapping;

public class UserHandler extends AbstractHandler {
    public UserHandler(RequestMapping mapping) {
        super(mapping);
    }

    public UserHandler(RequestMapping... mappings) {
        super(mappings);
    }

    @Override
    public void handle(ServletRequest servletRequest, ServletResponse servletResponse) {

    }
}
