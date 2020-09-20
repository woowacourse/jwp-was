package webserver;

import java.lang.reflect.Method;
import java.util.List;

import webserver.controller.Handlers;

public interface HandlerMapping {
    Method mapping(ServletRequest request);
}
