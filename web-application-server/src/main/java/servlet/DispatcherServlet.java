package servlet;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import http.request.HttpRequest;
import http.response.HttpResponse;

public class DispatcherServlet implements HttpServlet {
    // private static final List<HandlerMapping> handlerMappings;

    // static {
    //     handlerMappings = Arrays.asList(new ServletMapping(), new StaticResourceMapping());
    // }
    //
    private final List<HandlerMapping> handlerMappings;

    public DispatcherServlet(final List<HttpServlet> httpServlets) {
        this.handlerMappings = Arrays.asList(new ServletMapping(httpServlets), new StaticResourceMapping());
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        for (HandlerMapping handlerMapping : handlerMappings) {
            HttpServlet httpServlet = handlerMapping.getServlet(httpRequest);
            if (Objects.nonNull(httpServlet)) {
                httpServlet.service(httpRequest, httpResponse);
                return;
            }
        }
        httpResponse.notFound();
    }
}
