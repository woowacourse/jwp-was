package webserver.servlet;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.view.View;

public interface HttpServlet {
    View run(HttpRequest request, HttpResponse response);
}
