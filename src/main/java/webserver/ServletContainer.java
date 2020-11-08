package webserver;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ServletContainer {
    private static ServletContainer SINGLE_INSTANCE = new ServletContainer();

    private final Map<String, HttpServlet> servlets;
    private final Map<String, Supplier<HttpServlet>> servletGenerator;

    private ServletContainer() {
        this.servlets = new HashMap<>();
        this.servletGenerator = new HashMap<>();
        this.servletGenerator.put("/user/create", UserServlet::new);
    }

    public static ServletContainer getInstance() {
        return SINGLE_INSTANCE;
    }

    public HttpServlet get(String url) {
        if (servlets.containsKey(url)) {
            return servlets.get(url);
        }
        return servlets.computeIfAbsent(url, newUrl -> servletGenerator.get(newUrl).get());
    }
}
