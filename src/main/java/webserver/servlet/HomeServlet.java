package webserver.servlet;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.view.View;

import java.io.IOException;

public class HomeServlet extends RequestServlet {
    private String url = "/";

    @Override
    public View doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        httpResponse.ok();
        return new View("index");
    }

    @Override
    protected String getUrl() {
        return url;
    }
}

