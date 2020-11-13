package webserver.servlet;

import webserver.controller.Controller;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestMapping;
import webserver.http.response.HttpResponse;

public class HttpServlet {
    public void doDispatch(HttpRequest request, HttpResponse response) {
        RequestMapping requestMapping = request.toRequestMapping();
        Controller controller = ControllerMapping.get(requestMapping);
        View view = controller.doService(request, response);
        view.render(request, response);
    }
}
