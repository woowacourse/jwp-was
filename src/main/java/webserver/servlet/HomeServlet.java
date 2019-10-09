package webserver.servlet;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.resolver.Resolver;
import webserver.view.ModelAndView;

import java.io.IOException;

public class HomeServlet extends AbstractRequestServlet {
    private final String url = "/";
    private Resolver resolver;
    public HomeServlet(Resolver resolver) {
        this.resolver = resolver;
    }

    @Override
    protected ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        return new ModelAndView(resolver.createView("/index"));
    }

    @Override
    protected String getUrl() {
        return url;
    }
}


