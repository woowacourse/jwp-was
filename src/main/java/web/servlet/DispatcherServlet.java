package web.servlet;

import web.HandlerMapping;
import web.HttpRequest;
import web.HttpResponse;

public class DispatcherServlet implements Controller {

    @Override
    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        Controller controller = HandlerMapping.find(httpRequest);
        controller.doService(httpRequest, httpResponse);
    }
}
