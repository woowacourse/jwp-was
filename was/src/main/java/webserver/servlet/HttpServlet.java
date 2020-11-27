package webserver.servlet;

import java.util.Optional;

import webserver.controller.Controller;
import webserver.controller.ControllerMapping;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestLine;
import webserver.http.request.RequestMapping;
import webserver.http.request.RequestURI;
import webserver.http.response.HttpResponse;

public class HttpServlet {
    private static final HttpServlet HTTP_SERVLET = new HttpServlet();

    private HttpServlet() {
    }

    public static HttpServlet getInstance() {
        return HTTP_SERVLET;
    }

    public void doDispatch(HttpRequest request, HttpResponse response) {
        RequestMapping requestMapping = request.toRequestMapping();
        Optional<Controller> controller = ControllerMapping.get(requestMapping);

        if (controller.isPresent()) {
            AbstractView view = controller.get().doService(request, response);
            view.render(request, response);
            return;
        }

        renderResource(request, response);
    }

    private void renderResource(HttpRequest request, HttpResponse response) {
        HttpRequestLine requestLine = request.getRequestLine();
        RequestURI uri = requestLine.getRequestURI();
        new FileView(uri).render(request, response);
    }
}
