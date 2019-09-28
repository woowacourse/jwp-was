package webserver.servlet;

import db.DataBase;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.resolver.Resolver;
import webserver.session.HttpSession;
import webserver.view.ModelAndView;
import webserver.view.RedirectView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class UserListServlet extends AbstractRequestServlet {
    private final String url = "/user/list";

    public UserListServlet(Resolver resolver) {
        super(resolver);
    }

    @Override
    public ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        Map<String, Object> model = new HashMap<>();
        HttpSession httpSession = httpRequest.getSession();
        if (httpSession.getAttribute("user") != null) {
            model.put("users", DataBase.findAll());
            return new ModelAndView(resolver.createView("/user/list"), model);
        }
        return new ModelAndView(new RedirectView("/user/login"));

    }

    @Override
    protected String getUrl() {
        return url;
    }
}
