package kr.wootecat.dongle;

import java.util.Map;
import java.util.function.Supplier;

import kr.wootecat.dongle.core.servlet.HttpServlet;
import kr.wootecat.dongle.core.servlet.ServletMapper;

public class ServletConfig {

    private final Map<String, Supplier<HttpServlet>> servletMapping;

    public ServletConfig(
            Map<String, Supplier<HttpServlet>> servletMapping) {
        this.servletMapping = servletMapping;
    }

    public void init() {
        ServletMapper mapper = ServletMapper.getInstance();
        mapper.putAll(servletMapping);
    }
}
