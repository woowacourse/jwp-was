package web.servlet;

import web.HandlerMapping;
import web.HttpRequest;
import web.HttpResponse;

public class DispatcherServlet implements Servlet {

    @Override
    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        Servlet servlet = HandlerMapping.find(httpRequest);
        servlet.doService(httpRequest, httpResponse);
    }
}
