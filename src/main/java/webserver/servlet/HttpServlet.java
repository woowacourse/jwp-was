package webserver.servlet;

import webserver.http.request.HttpRequest;
import webserver.http.request.RequestUri;
import webserver.http.response.HttpResponse;
import webserver.view.ModelAndView;

import java.io.IOException;

public interface HttpServlet {
    boolean canMapping(RequestUri requestUri);

    ModelAndView run(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}
