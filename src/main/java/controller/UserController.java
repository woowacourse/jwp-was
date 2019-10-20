package controller;

import webserver.common.HttpSession;
import webserver.common.ModelAndView;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.view.HtmlView;
import webserver.view.RedirectView;

import static controller.support.Constants.LOGINED_KEY;
import static controller.support.Constants.LOGINED_VALUE_TRUE;

public class UserController extends AbstractController {

    @Override
    protected ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession httpSession = httpRequest.getHttpSession();
        if (LOGINED_VALUE_TRUE.equals(httpSession.getAttribute(LOGINED_KEY))) {
            modelAndView.setView(new RedirectView("/index.html"));
            return modelAndView;
        }
        modelAndView.setView(new HtmlView("/user/form"));
        return modelAndView;
    }
}
