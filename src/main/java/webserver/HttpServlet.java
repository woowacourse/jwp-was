package webserver;

import http.HttpRequest;

public interface HttpServlet {
    void doGet(HttpRequest request);
    void doPost(HttpRequest request);
    void doPut(HttpRequest request);
    void doDelete(HttpRequest request);
}
