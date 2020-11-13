package webserver.servlet;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import net.slipp.presentation.UserServlet;

import webserver.servlet.exception.ResourceNotFoundException;

public class ServletMapper {

    private static final ServletMapper SINGLE_INSTANCE = new ServletMapper();

    private final Map<String, HttpServlet> servlets = new HashMap<>();
    private final Map<String, Supplier<HttpServlet>> servletGenerator = new HashMap<>();

    private ServletMapper() {
        // SINGLE_INSTANCE.mappingServletGenerator("/user/create", UserServlet::new);
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
            Supplier<HttpServlet> httpServletSupplier = servletGenerator.get(newUrl);
            if (httpServletSupplier == null) {
                throw new ResourceNotFoundException(newUrl);
            }
            return httpServletSupplier.get();
        });
    }

    public void mappingServletGenerator(String url, Supplier<HttpServlet> mappingServletGenerator) {
        this.servletGenerator.put(url, mappingServletGenerator);
    }
}
