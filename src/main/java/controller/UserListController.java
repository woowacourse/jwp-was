package controller;

import db.DataBase;
import http.*;
import http.exception.NotFoundSessionAttributeException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HandlebarsHelper;

import java.io.IOException;
import java.net.URISyntaxException;

public class UserListController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserListController.class);

    public static final String PATH = "/user/list";

    @Override
    Object doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        LOGGER.debug("userList request get: {}", httpRequest.getUri());
        try {
            HttpSession session = HttpSessionStore.getSession(httpRequest.getSessionId());
            LOGGER.debug("session id: {}", session.getId());

            LOGGER.debug("session attribute, {}", (String) session.getAttributes("logined"));

            String sessionAttribute = (String) session.getAttributes("logined");

            if (sessionAttribute.equals("true")) {

                ModelAndView modelAndView = new ModelAndView(new TemplateView("list.html"));
                httpResponse.setHttpResponseBody(new HttpResponseBody(HandlebarsHelper.apply(DataBase.findAll())));
                return modelAndView;
            }
        } catch (NotFoundSessionAttributeException e) {
        }

        return new RedirectView("login_failed.html");
    }
}
