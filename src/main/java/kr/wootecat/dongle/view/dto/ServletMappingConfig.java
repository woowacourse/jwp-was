package kr.wootecat.dongle.view.dto;

import java.util.Map;
import java.util.function.Supplier;

import kr.wootecat.dongle.model.servlet.HttpServlet;

public class ServletMappingConfig {

    private final Map<String, Supplier<HttpServlet>> servletMapping;

    public ServletMappingConfig(Map<String, Supplier<HttpServlet>> servletMapping) {
        this.servletMapping = servletMapping;
    }

    public Map<String, Supplier<HttpServlet>> getServletMapping() {
        return servletMapping;
    }
}
