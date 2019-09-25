package webserver.servlet;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface HttpServlet {
    HttpResponse run(HttpRequest httpRequest) throws IOException, URISyntaxException;
}
