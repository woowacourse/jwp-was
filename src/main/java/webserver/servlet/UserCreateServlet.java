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
    public static final String USER_ID_KEY = "userId";
    public static final String PASSWORD_KEY = "password";
    public static final String NAME_KEY = "name";
    public static final String EMAIL_KEY = "email";
    private final String url = "/user/create";
    private Resolver resolver;
    public UserCreateServlet(Resolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public ModelAndView doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        User user = createUser(httpRequest);
        logger.debug(">>> User : {}", user);
        DataBase.addUser(user);
        return new ModelAndView(resolver.createView("redirect:/user/login"));
    }

    private User createUser(HttpRequest httpRequest){
        return new User(httpRequest.getBody(USER_ID_KEY), httpRequest.getBody(PASSWORD_KEY), httpRequest.getBody(NAME_KEY), httpRequest.getBody(EMAIL_KEY));
    }

    @Override
    protected String getUrl() {
        return url;
    }
}
