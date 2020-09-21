package webserver.handlermapping;

import webserver.controller.annotation.RequestMapping;
import webserver.request.ServletRequest;

public interface HandlerMappingStrategy {
    boolean isSupport(ServletRequest request, RequestMapping annotation);
}
