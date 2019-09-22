package http.controller;

import http.model.ServletRequest;
import http.model.ServletResponse;
import http.supoort.RequestMapping;

public class FileResourceController extends AbstractController {

    public FileResourceController(RequestMapping mapping) {
        super(mapping);
    }

    public FileResourceController(RequestMapping... mappings) {
        super(mappings);
    }

    @Override
    public void handle(ServletRequest servletRequest, ServletResponse servletResponse) {

    }
}
