package webserver.http.servlet;

import webserver.http.request.HttpRequest;
import webserver.http.request.RequestMapping;
import webserver.http.response.HttpResponse;
import webserver.http.servlet.controller.Controller;
import webserver.http.servlet.controller.ControllerMapping;

public class HttpServlet {
    private static final HttpServlet HTTP_SERVLET = new HttpServlet();

    private HttpServlet() {
    }

    public static HttpServlet getInstance() {
        return HTTP_SERVLET;
    }

    public void doDispatch(HttpRequest request, HttpResponse response) {
        RequestMapping requestMapping = request.toRequestMapping();
        Controller controller = ControllerMapping.get(requestMapping);
        AbstractView view = controller.doService(request, response);
        view.render(request, response);
    }
}
