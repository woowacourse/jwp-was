package was.http.servlet;

import server.http.request.HttpRequest;
import server.http.response.HttpResponse;

public interface Servlet {
    HttpResponse service(HttpRequest httpRequest);
}
