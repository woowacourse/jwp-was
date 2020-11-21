package webserver.handlermapping;

import java.lang.reflect.Method;

import webserver.request.ServletRequest;

public interface HandlerMapping {
    Method mapping(ServletRequest request);
}
