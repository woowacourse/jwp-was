package webserver.dispatcher;

import webserver.handler.MappingHandler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.servlet.HttpServlet;
import webserver.servlet.RequestServlet;
import webserver.view.View;

import java.io.IOException;

public class RequestDispatcher {
    public static void dispatch(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        HttpServlet httpServlet = MappingHandler.getServlets(httpRequest.getUri());
        View view = httpServlet.run(httpRequest, httpResponse);
    }
}
