package webserver;

import webserver.controller.Controller;
import webserver.controller.StaticController;
import webserver.domain.request.HttpRequest;

public class RequestMapping {
    private final ServletContainer servletContainer;
    private final StaticController staticController;

    public RequestMapping(ServletContainer servletContainer, StaticController staticController) {
        this.servletContainer = servletContainer;
        this.staticController = staticController;
    }

    public Controller getController(HttpRequest httpRequest) {
        if (servletContainer.hasMappingServlet(httpRequest)) {
            return servletContainer.getController(httpRequest);
        }

        if (staticController.isForStaticContent(httpRequest)) {
            return staticController;
        }

        throw new UnsupportedRequestUrlException(httpRequest.getDefaultPath());
    }
}
