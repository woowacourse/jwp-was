package webserver.servlet;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.resolver.Resolver;
import webserver.view.ModelAndView;

import java.io.IOException;

public class UserListServlet extends RequestServlet {
    private final String url = "/user/list";

    public UserListServlet(Resolver resolver) {
        super(resolver);
    }

    @Override
    public ModelAndView doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        return super.doPost(httpRequest, httpResponse);
    }

    @Override
    protected String getUrl() {
        return url;
    }
}
