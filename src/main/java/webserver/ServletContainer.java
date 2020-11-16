package webserver;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.controller.Controller;
import webserver.http.request.HttpRequest;

public class ServletContainer {
    private static final Logger logger = LoggerFactory.getLogger(ServletContainer.class);
    private final Map<String, String> servletNameMapper;
    private final Map<String, Controller> servletContainer;

    public ServletContainer() {
        servletNameMapper = new HashMap<>();
        servletContainer = new HashMap<>();
        servletNameMapper.put("/user/create", "webserver.controller.UserCreateController");
        logger.info("ServletContainer has loaded.");
    }

    public boolean hasMappingServlet(HttpRequest httpRequest) {
        return servletNameMapper.containsKey(httpRequest.getDefaultPath());
    }

    public Controller getController(HttpRequest httpRequest) {
        return getInstance(httpRequest.getDefaultPath());
    }

    private synchronized Controller getInstance(String requestPath) {
        try {
            if (servletContainer.get(requestPath) == null) {
                String className = servletNameMapper.get(requestPath);
                Class clazz = Class.forName(className);
                servletContainer.put(requestPath, (Controller)clazz.newInstance());
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NullPointerException e) {
            logger.error(e.getMessage());
            throw new CannotInitServletException(requestPath);
        }
        return servletContainer.get(requestPath);
    }
}
