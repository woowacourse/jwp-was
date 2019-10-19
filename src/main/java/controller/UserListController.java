package controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.common.HttpSession;
import webserver.common.ModelAndView;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.view.HtmlView;
import webserver.view.RedirectView;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static controller.support.Constants.LOGINED_KEY;
import static controller.support.Constants.LOGINED_VALUE_TRUE;

public class UserListController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(UserListController.class);

    @Override
    public ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession httpSession = httpRequest.getHttpSession();

        if (LOGINED_VALUE_TRUE.equals(httpSession.getAttribute(LOGINED_KEY))) {
            log.debug("login success");
            Map<String, Object> model = new HashMap<>();
            Collection<User> users = DataBase.findAll();
            model.put("users", users);

            modelAndView.setView(new HtmlView("/user/list"));
            modelAndView.setModel(model);
            return modelAndView;
        }
        modelAndView.setView(new RedirectView("/user/login.html"));
        log.debug("login fail");
        return modelAndView;
    }
}
