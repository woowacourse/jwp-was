package webserver.servlet;

import webserver.http.request.HttpRequest;
import webserver.http.request.RequestUri;
import webserver.http.response.FileType;
import webserver.http.response.HttpResponse;
import webserver.view.ModelAndView;

import java.io.IOException;

public class ErrorServlet extends RequestServlet {
    private final String url = "/error";

    @Override
    public boolean canMapping(RequestUri requestUri) {
        return super.canMapping(requestUri);
    }

    @Override
    public ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        httpResponse.appendHeader("Content-Type", FileType.HTML + "; charset=utf-8");
        return new ModelAndView("error");
    }

    @Override
    protected String getUrl() {
        return url;
    }
}
