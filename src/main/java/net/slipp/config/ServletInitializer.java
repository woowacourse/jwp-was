package net.slipp.config;

import webserver.servlet.ServletMapper;

public class ServletInitializer {
    private static final ServletMapper SERVLET_MAPPER = ServletMapper.getInstance();

    static {
        // SERVLET_MAPPER.mappingServletGenerator("/user/create", UserServlet::new);
    }
}
