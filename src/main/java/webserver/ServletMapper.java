package webserver;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import webserver.exception.ResourceNotFoundException;

public class ServletMapper {
    private static final ServletMapper SINGLE_INSTANCE = new ServletMapper();

    private final Map<String, HttpServlet> servlets;
    private final Map<String, Supplier<HttpServlet>> servletGenerator;

    private ServletMapper() {
        this.servlets = new HashMap<>();
        this.servletGenerator = new HashMap<>();
        this.servletGenerator.put("/user/create", UserServlet::new);
    }

    public static ServletMapper getInstance() {
        return SINGLE_INSTANCE;
    }

    public HttpServlet get(String url) {
        if (servlets.containsKey(url)) {
            return servlets.get(url);
        }
        return servlets.computeIfAbsent(url, newUrl -> {
            final Supplier<HttpServlet> httpServletSupplier = servletGenerator.get(newUrl);
            if (httpServletSupplier == null) {
                throw new ResourceNotFoundException();
            }
            return httpServletSupplier.get();
        });
    }
}
