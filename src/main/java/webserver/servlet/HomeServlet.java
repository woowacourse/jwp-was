package webserver.servlet;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.resolver.Resolver;
import webserver.view.ForwardView;
import webserver.view.ModelAndView;
import webserver.view.View;

import java.io.IOException;
import java.net.URISyntaxException;

public class HomeServlet extends RequestServlet {
    private final String url = "/";

    public HomeServlet(Resolver resolver) {
        super(resolver);
    }

    @Override
    protected ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        return new ModelAndView();
    }

    @Override
    protected String getUrl() {
        return url;
    }
}

