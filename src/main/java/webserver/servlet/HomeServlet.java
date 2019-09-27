package webserver.servlet;

import webserver.http.request.HttpRequest;
import webserver.http.response.FileType;
import webserver.http.response.HttpResponse;
import webserver.view.ModelAndView;
import webserver.view.View;

import java.io.IOException;

public class HomeServlet extends RequestServlet {
    private final String url = "/";

    @Override
    public ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        httpResponse.ok();
        httpResponse.appendHeader("Content-Type", FileType.HTML + "; charset=utf-8");
        return new ModelAndView("index");
    }

    @Override
    protected String getUrl() {
        return url;
    }
}

