package webserver.servlet;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.view.ModelAndView;

import java.io.IOException;
import java.net.URISyntaxException;

public interface HttpServlet {
    boolean canMapping(HttpRequest httpRequest);

    ModelAndView run(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException;
}
