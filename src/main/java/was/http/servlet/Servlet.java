package was.http.servlet;

import was.http.request.HttpRequest;
import was.http.response.HttpResponse;

public interface Servlet {
    HttpResponse service(HttpRequest httpRequest);
}
