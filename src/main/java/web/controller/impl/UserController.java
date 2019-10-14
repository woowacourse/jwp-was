package web.controller.impl;

import org.slf4j.Logger;
import web.controller.AbstractController;
import web.db.DataBase;
import web.model.User;
import webserver.message.request.Request;
import webserver.message.response.Response;
import webserver.view.ModelAndView;
import webserver.view.RedirectView;

import static org.slf4j.LoggerFactory.getLogger;

public class UserController extends AbstractController {
    private static final Logger LOG = getLogger(UserController.class);

    @Override
    protected ModelAndView doPost(final Request request, final Response response) {
        final String userId = request.getQueryValue("userId");
        final String password = request.getQueryValue("password");
        final String name = request.getQueryValue("name");
        final String email = request.getQueryValue("email");
        final User user = new User(userId, password, name, email);

        DataBase.addUser(user);
        LOG.debug(user.toString());

        return new ModelAndView(new RedirectView("/"));
    }
}
