package webserver.handlermapping;

import java.util.Arrays;

import webserver.controller.annotation.RequestMapping;
import webserver.request.ServletRequest;

public class DefaultHandlerMappingStrategy implements HandlerMappingStrategy {
    @Override
    public boolean isSupport(ServletRequest request, RequestMapping annotation) {
        return Arrays.asList(annotation.value()).contains(request.getPath())
            && annotation.type().equals(request.getMethod());
    }
}
