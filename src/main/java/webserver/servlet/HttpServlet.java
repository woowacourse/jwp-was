package webserver.servlet;

import webserver.http.request.HttpRequest;
import webserver.http.request.RequestUri;
import webserver.http.response.HttpResponse;
import webserver.view.View;

import java.io.IOException;

public interface HttpServlet {
    boolean canMapping(RequestUri requestUri);

    View run(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}
