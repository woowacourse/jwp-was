package webserver.servlet;

import webserver.domain.request.HttpRequest;
import webserver.domain.response.HttpResponse;

public interface Servlet {
    HttpResponse service(HttpRequest httpRequest);
}
