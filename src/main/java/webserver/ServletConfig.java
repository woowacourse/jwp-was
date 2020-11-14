package webserver;

import java.util.Map;
import java.util.function.Supplier;

import webserver.servlet.HttpServlet;
import webserver.servlet.ServletMapper;

public class ServletConfig {

    private final Map<String, Supplier<HttpServlet>> servletMapping;

    public ServletConfig(
            Map<String, Supplier<HttpServlet>> servletMapping) {
        this.servletMapping = servletMapping;
    }

    public void mapServlet() {
        ServletMapper mapper = ServletMapper.getInstance();
        mapper.putAll(servletMapping);
    }
}
