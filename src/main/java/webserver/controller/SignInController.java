package webserver.controller;

import db.DataBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.ModelAndView;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class SignInController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(SignInController.class);

    @Override
    public ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("/user/login");
        return modelAndView;
    }

    @Override
    public String doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");

        if (!DataBase.contains(userId)) {
            httpResponse.addHeader("Set-Cookie", "logined=false; Path=/");
            log.debug("Not Found UserId");
            return "redirect:/user/login_failed.html";
        }

        if (DataBase.match(userId, password)) {
            httpResponse.addHeader("Set-Cookie", "logined=true; Path=/");
            log.debug("{} login", userId);
            return "redirect:/index.html";
        }

        httpResponse.addHeader("Set-Cookie", "logined=false; Path=/");
        log.debug("Not Match UserId And Password");
        return "redirect:/user/login_failed.html";
    }
}
