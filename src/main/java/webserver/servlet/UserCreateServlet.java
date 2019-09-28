package webserver.servlet;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.resolver.Resolver;
import webserver.view.ModelAndView;

import java.io.IOException;

public class UserCreateServlet extends AbstractRequestServlet {
    private static final Logger logger = LoggerFactory.getLogger(AbstractRequestServlet.class);
    private final String url = "/user/create";

    public UserCreateServlet(Resolver resolver) {
        super(resolver);
    }

    @Override
    public ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        return new ModelAndView(resolver.createView(url));
    }

    @Override
    public ModelAndView doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        User user = new User(httpRequest.getBody("userId"), httpRequest.getBody("password"), httpRequest.getBody("name"), httpRequest.getBody("email"));
        logger.debug(">>> User : {}", user);
        DataBase.addUser(user);
        return new ModelAndView(resolver.createView("redirect:/user/login"));
    }

    @Override
    protected String getUrl() {
        return url;
    }
}
