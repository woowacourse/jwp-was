package http.controller;

import http.model.common.HttpProtocols;
import http.model.request.ServletRequest;
import http.model.response.HttpStatus;
import http.model.response.ServletResponse;
import http.supoort.RequestMapping;
import utils.FileIoUtils;

public class FileResourceController extends AbstractController {

    public FileResourceController(RequestMapping mapping) {
        super(mapping);
    }

    public FileResourceController(RequestMapping... mappings) {
        super(mappings);
    }

    @Override
    public void handle(ServletRequest servletRequest, ServletResponse servletResponse) {
        String resource = servletRequest.getHttpUri().getResourceLocation();

        servletResponse.setProtocols(HttpProtocols.HTTP1);
        servletResponse.setHttpStatus(HttpStatus.OK);
        servletResponse.addHeader("Content-Type", "text/html");
        servletResponse.setBody(FileIoUtils.loadFileFromClasspath("./templates" + resource));
    }
}
