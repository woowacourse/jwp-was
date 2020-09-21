package webserver.handlermapping;

import webserver.controller.annotation.RequestMapping;
import webserver.request.MethodType;
import webserver.request.ServletRequest;

public class StaticResourceHandlerMappingStrategy implements HandlerMappingStrategy {
    @Override
    public boolean isSupport(ServletRequest request, RequestMapping annotation) {
        return request.hasStaticResource() && annotation.type().equals(MethodType.GET) && annotation.isResource();
    }
}
