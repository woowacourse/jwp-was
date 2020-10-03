package webserver;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.domain.request.HttpRequest;
import webserver.domain.response.HttpResponse;
import webserver.servlet.Servlet;

public class ServletContainer {
    private static final Logger logger = LoggerFactory.getLogger(ServletContainer.class);
    private static final Map<String, String> servletNameMapper = new HashMap<>();
    private static final Map<String, Servlet> servletContainer = new HashMap<>();

    static {
        servletNameMapper.put("/user/create", "webserver.servlet.UserCreate");
        logger.info("ServletContainer has loaded.");
    }

    public static HttpResponse executeServlet(HttpRequest httpRequest) {
        Servlet servlet = getInstance(httpRequest.getPath());
        return servlet.service(httpRequest);
    }

    private static synchronized Servlet getInstance(String requestPath) {
        try {
            if (servletContainer.get(requestPath) == null) {
                String className = servletNameMapper.get(requestPath);
                Class clazz = Class.forName(className);
                servletContainer.put(requestPath, (Servlet)clazz.newInstance());
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            logger.error(e.getMessage());
            throw new CannotInitServletException(requestPath);
        }
        return servletContainer.get(requestPath);
    }
}
