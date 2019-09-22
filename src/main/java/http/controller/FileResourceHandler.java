package http.controller;

import http.model.ServletRequest;
import http.model.ServletResponse;
import http.supoort.RequestMapping;

public class FileResourceHandler extends AbstractHandler {

    public FileResourceHandler(RequestMapping mapping) {
        super(mapping);
    }

    public FileResourceHandler(RequestMapping... mappings) {
        super(mappings);
    }

    @Override
    public void handle(ServletRequest servletRequest, ServletResponse servletResponse) {

    }
}
