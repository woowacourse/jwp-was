package webserver.controller;

import ch.qos.logback.core.db.dialect.DBUtil;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.ModelAndView;
import webserver.controller.request.HttpRequest;
import webserver.controller.response.HttpResponse;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class UserListController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);
    private static final String USER_LIST_URL = "/user/list.html";
    private static final String NON_LOGIN_REDIRECT_URL = "/user/login.html";


    @Override
    protected HttpResponse doGet(HttpRequest httpRequest) {
        String[] logined = httpRequest.getHeaderFieldValue("Cookie").split("=");

        if(logined[1].equals("true")) {
            String path = NON_STATIC_FILE_PATH + USER_LIST_URL;
            Optional<byte []> maybeBody = FileIoUtils.loadFileFromClasspath(path);

            Collection<User> users = DataBase.findAll();
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addModel("users", users);
            modelAndView.setViewName(path);
            return HttpResponse.ok(httpRequest,maybeBody.get());
        }

        return HttpResponse.sendRedirect(httpRequest, NON_LOGIN_REDIRECT_URL, false);
    }
}
