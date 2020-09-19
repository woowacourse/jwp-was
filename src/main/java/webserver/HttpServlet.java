package webserver;

import http.HttpRequest;
import http.HttpResponse;

public interface HttpServlet {
    void doGet(HttpRequest request, HttpResponse response);

    void doPost(HttpRequest request, HttpResponse response);

    void doPut(HttpRequest request, HttpResponse response);

    void doDelete(HttpRequest request, HttpResponse response);
}
