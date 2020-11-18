package controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import annotation.RequestMapping;
import http.HttpRequest;
import http.HttpResponse;

public class DispatcherServlet implements HttpServlet {
    private static final Map<String, HttpServlet> SERVLET_MATCHER;

    static {
        List<HttpServlet> servlets = Arrays.asList(new UserController());
        SERVLET_MATCHER = servlets.stream()
            .collect(Collectors.toMap(
                servlet -> servlet.getClass().getAnnotation(RequestMapping.class).path(),
                servlet -> servlet));
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (!SERVLET_MATCHER.containsKey(httpRequest.getURI())) {
            httpResponse.notFound();
            return;
        }
        HttpServlet servlet = SERVLET_MATCHER.get(httpRequest.getURI());
        servlet.service(httpRequest, httpResponse);
    }
}
