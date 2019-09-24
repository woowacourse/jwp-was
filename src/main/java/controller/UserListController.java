package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import view.ModelAndView;
import view.View;

public class UserListController extends AbstractController {
    @Override
    public View doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        ModelAndView view = new ModelAndView("user/list.html");
        view.addAttribute("users", DataBase.findAll());
        return view;
    }
}
