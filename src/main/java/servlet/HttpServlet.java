package servlet;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface HttpServlet {
    void service(HttpRequest httpRequest, HttpResponse httpResponse);
}
